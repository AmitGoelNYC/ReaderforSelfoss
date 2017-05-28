package apps.amine.bou.readerforselfoss.adapters

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.support.constraint.ConstraintLayout
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import apps.amine.bou.readerforselfoss.R
import apps.amine.bou.readerforselfoss.api.selfoss.SelfossApi
import apps.amine.bou.readerforselfoss.api.selfoss.Sources
import apps.amine.bou.readerforselfoss.api.selfoss.SuccessResponse
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SourcesListAdapter(private val app: Activity, private val items: ArrayList<Sources>, private val api: SelfossApi) : RecyclerView.Adapter<SourcesListAdapter.ViewHolder>() {
    private val c: Context = app.baseContext
    private val generator: ColorGenerator = ColorGenerator.MATERIAL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.source_list_item, parent, false) as ConstraintLayout
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itm = items[position]

        val fHolder = holder
        if (itm.getIcon(c).isEmpty()) {
            val color = generator.getColor(itm.title)
            val textDrawable = StringBuilder()
            for (s in itm.title.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                textDrawable.append(s[0])
            }

            val builder = TextDrawable.builder().round()

            val drawable = builder.build(textDrawable.toString(), color)
            holder.sourceImage!!.setImageDrawable(drawable)
        } else {
            Glide.with(c).load(itm.getIcon(c)).asBitmap().centerCrop().into(object : BitmapImageViewTarget(holder.sourceImage) {
                override fun setResource(resource: Bitmap) {
                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(c.resources, resource)
                    circularBitmapDrawable.isCircular = true
                    fHolder.sourceImage!!.setImageDrawable(circularBitmapDrawable)
                }
            })
        }

        holder.sourceTitle!!.text = itm.title
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(internal val mView: ConstraintLayout) : RecyclerView.ViewHolder(mView) {
        var sourceImage: ImageView? = null
        var sourceTitle: TextView? = null

        init {

            handleClickListeners()
        }

        private fun handleClickListeners() {
            sourceImage = mView.findViewById(R.id.itemImage) as ImageView
            sourceTitle = mView.findViewById(R.id.sourceTitle) as TextView

            val deleteBtn = mView.findViewById(R.id.deleteBtn) as Button

            deleteBtn.setOnClickListener {
                val (id) = items[adapterPosition]
                api.deleteSource(id).enqueue(object : Callback<SuccessResponse> {
                    override fun onResponse(call: Call<SuccessResponse>, response: Response<SuccessResponse>) {
                        if (response.body() != null && response.body()!!.isSuccess) {
                            items.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            notifyItemRangeChanged(adapterPosition, itemCount)
                        } else {
                            Toast.makeText(app, "Petit soucis lors de la suppression de la source.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                        Toast.makeText(app, "Petit soucis lors de la suppression de la source.", Toast.LENGTH_SHORT).show()
                    }
                })
            }


        }
    }
}

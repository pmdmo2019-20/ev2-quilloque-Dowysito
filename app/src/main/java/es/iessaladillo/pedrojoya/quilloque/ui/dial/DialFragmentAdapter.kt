package es.iessaladillo.pedrojoya.quilloque.ui.dial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.Contact
import es.iessaladillo.pedrojoya.quilloque.data.Sugerencia
import es.iessaladillo.pedrojoya.quilloque.utils.createAvatarDrawable
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.contacts_fragment_item.view.*
import kotlinx.android.synthetic.main.contacts_fragment_item.view.imgAvatar
import kotlinx.android.synthetic.main.contacts_fragment_item.view.lblPhoneNumber
import kotlinx.android.synthetic.main.dial_fragment_item.view.*

class DialFragmentAdapter: RecyclerView.Adapter<DialFragmentAdapter.ViewHolder>() {

    private var data: List<Sugerencia> = emptyList()


    private var onItemClickListener: ((Int) -> Unit)? = null


    fun setOnItemClickListener(listener: ((Int) -> Unit)?){
        onItemClickListener= listener
    }

    fun getItem(position: Int):Sugerencia{
        return data[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.dial_fragment_item, parent, false)
        return ViewHolder(itemView,onItemClickListener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun submitList(contacts: List<Sugerencia>) {
        data=contacts
        notifyDataSetChanged()
    }


    inner class ViewHolder(
        override val containerView: View,
        onItemClickListener: ((Int) -> Unit)?
    ) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener {
                onItemClickListener?.invoke(adapterPosition)
            }
        }

        fun bind(sugerencia: Sugerencia) {
            sugerencia.run {
                if (this.contactName.isNullOrEmpty()){
                    containerView.lblContactName.text= this.phoneNumber
                    containerView.lblPhoneNumber.text = this.phoneNumber
                    containerView.imgAvatar.setImageDrawable(createAvatarDrawable("?"))
                }else{
                    containerView.lblContactName.text= this.contactName
                    containerView.lblPhoneNumber.text = this.phoneNumber
                    containerView.imgAvatar.setImageDrawable(createAvatarDrawable(this.contactName))
                }
            }
        }
    }


}
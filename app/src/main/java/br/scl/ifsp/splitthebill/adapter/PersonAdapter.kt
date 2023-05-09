package br.scl.ifsp.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.scl.ifsp.splitthebill.R
import br.scl.ifsp.splitthebill.databinding.TilePersonBinding
import br.scl.ifsp.splitthebill.model.Person

class PersonAdapter(context: Context, private val personList: MutableList<Person>): ArrayAdapter<Person>(context, R.layout.tile_person, personList) {
    private lateinit var tilePersonBinding: TilePersonBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val person = personList[position]

        var tilePersonView = convertView
        if (tilePersonView == null) {
            tilePersonBinding = TilePersonBinding.inflate(context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater, parent, false)
            tilePersonView = tilePersonBinding.root

            val tilePersonViewHolder = TilePersonViewHolder(
                tilePersonBinding.personNameText,
                tilePersonBinding.personSpentValueText,
                tilePersonBinding.personToPayText,
            )

            tilePersonView.tag = tilePersonViewHolder
        }

        (tilePersonView.tag as TilePersonViewHolder).personNameText.text = person.name
        (tilePersonView.tag as TilePersonViewHolder).personSpentValueText.text = "R$ ${person.spent}"
        (tilePersonView.tag as TilePersonViewHolder).personToPayText.text = "R$ ${person.toPay}"

        return tilePersonView
    }

    private data class TilePersonViewHolder(
        val personNameText: TextView,
        val personSpentValueText: TextView,
        val personToPayText: TextView
    )
}
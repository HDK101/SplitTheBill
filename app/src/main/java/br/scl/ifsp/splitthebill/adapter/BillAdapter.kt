package br.scl.ifsp.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.scl.ifsp.splitthebill.R
import br.scl.ifsp.splitthebill.databinding.TileBillBinding
import br.scl.ifsp.splitthebill.model.Bill

class BillAdapter(context: Context, private val billList: MutableList<Bill>): ArrayAdapter<Bill>(context, R.layout.tile_bill, billList) {
    private lateinit var tileBillBinding: TileBillBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val bill = billList[position]

        var tileBillView = convertView
        if (tileBillView == null) {
            tileBillBinding = TileBillBinding.inflate(context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater, parent, false)
            tileBillView = tileBillBinding.root

            val tileBillViewHolder = TileBillViewHolder(
                tileBillBinding.billName,
                tileBillBinding.billTotalValue,
                tileBillBinding.billPersons,
            )

            tileBillView.tag = tileBillViewHolder
        }

        (tileBillView.tag as TileBillViewHolder).billName.text = bill.name
        (tileBillView.tag as TileBillViewHolder).billTotalValue.text = bill.totalSpent.toString()
        (tileBillView.tag as TileBillViewHolder).billName.text = bill.calculateSplitValue().toString()

        bill.calculatetoPayPerPerson()

        Log.d("bill", bill.persons.get(0).toPay.toString())

        return tileBillView
    }

    private data class TileBillViewHolder(
        val billName: TextView,
        val billTotalValue: TextView,
        val billPersons: TextView
    )
}
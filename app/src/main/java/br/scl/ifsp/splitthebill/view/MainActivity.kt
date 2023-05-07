package br.scl.ifsp.splitthebill.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import br.scl.ifsp.splitthebill.R
import br.scl.ifsp.splitthebill.adapter.BillAdapter
import br.scl.ifsp.splitthebill.databinding.ActivityMainBinding
import br.scl.ifsp.splitthebill.model.Bill

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var billAdapter: BillAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        val bill = Bill("Nome teste", 40.0)

        val person = Bill.Person("Lucas", 50.0, 0.0)

        bill.persons.add(person)

        billAdapter = BillAdapter(this, mutableListOf(bill))

        amb.list.adapter = billAdapter

        amb.list.onItemClickListener =
            AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                Log.d("walter", "hello!")
            }

        registerForContextMenu(amb.list)
    }
}
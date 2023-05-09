package br.scl.ifsp.splitthebill.view

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.scl.ifsp.splitthebill.R
import br.scl.ifsp.splitthebill.SplitTheBillApplication
import br.scl.ifsp.splitthebill.adapter.PersonAdapter
import br.scl.ifsp.splitthebill.controller.PersonController
import br.scl.ifsp.splitthebill.databinding.ActivityMainBinding
import br.scl.ifsp.splitthebill.model.Person

class MainActivity : BaseActivity() {
    private val amb: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val personController: PersonController by lazy { PersonController(this) }
    private var personList = mutableListOf<Person>()
    private lateinit var billAdapter: PersonAdapter
    private lateinit var carl: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

//        personList = (applicationContext as SplitTheBillApplication).getPersonRoom().getPersonDao().retrieve()
        billAdapter = PersonAdapter(this, personList)
        amb.list.adapter = billAdapter
        refreshPersonList()

        carl =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    refreshPersonList()
                }
            }

        amb.list.onItemClickListener =
            AdapterView.OnItemClickListener { p0, p1, position, p3 ->
                val person = personList[position]
                val personIntent = Intent(this@MainActivity, PersonActivity::class.java)
                personIntent.putExtra(EXTRA_PERSON, person)
                personIntent.putExtra(EXTRA_OPERATION, PersonActivity.Operation.EDIT)
                carl.launch(personIntent)
            }

        registerForContextMenu(amb.list)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addPerson -> {
                val personIntent = Intent(this@MainActivity, PersonActivity::class.java)
                personIntent.putExtra(EXTRA_OPERATION, PersonActivity.Operation.CREATE)
                carl.launch(personIntent)
                true
            }
            R.id.clearAll -> {
                AlertDialog.Builder(this)
                    .setMessage("Tem certeza que deseja apagar todas as pessoas?")
                    .setPositiveButton(
                        "Sim"
                    ) { _, _ ->
                        personController.clear {
                            refreshPersonList()
                        }
                    }
                    .setNegativeButton("Não", null)
                    .show()
                true
            }
            else -> false
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.menu_person, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        val person = personList[position]

        return when (item.itemId) {
            R.id.editPerson -> {
                val personIntent = Intent(this@MainActivity, PersonActivity::class.java)
                personIntent.putExtra(EXTRA_OPERATION, PersonActivity.Operation.EDIT)
                personIntent.putExtra(EXTRA_PERSON, person)
                carl.launch(personIntent)
                true
            }
            R.id.deletePerson -> {
                personController.delete(person) {
                    runOnUiThread {
                        refreshPersonList()
                        Toast.makeText(this, "Pessoa removida", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun updatePersonPerPay() {
        supportActionBar?.subtitle = ""

        if (personList.isEmpty()) return

        val total = personList
            .map { person -> person.spent }
            .reduce { a, b -> a + b }
        val limitPerPerson = total / personList.size.toDouble()

        supportActionBar?.subtitle = "Total: R$ ${String.format("%.2f", total)}"

        personList.forEach { person ->
            person.toPay = limitPerPerson - person.spent
        }
    }

    fun refreshPersonList() {
        personController.list { persons ->
            personList.clear()
            personList.addAll(persons)
            runOnUiThread {
                updatePersonPerPay()
                billAdapter.notifyDataSetChanged()
            }
        }
    }
}
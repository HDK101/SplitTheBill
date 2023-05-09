package br.scl.ifsp.splitthebill.view

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.scl.ifsp.splitthebill.R
import br.scl.ifsp.splitthebill.SplitTheBillApplication
import br.scl.ifsp.splitthebill.controller.PersonController
import br.scl.ifsp.splitthebill.databinding.ActivityPersonBinding
import br.scl.ifsp.splitthebill.model.Person
import kotlinx.parcelize.Parcelize

class PersonActivity : BaseActivity() {
    enum class Operation {
        CREATE,
        EDIT
    }

    private val personController: PersonController by lazy { PersonController(this) }

    private val abb: ActivityPersonBinding by lazy { ActivityPersonBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(abb.root)

        val receivedPerson = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_PERSON, Person::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_PERSON)
        }

        val operation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_OPERATION, Operation::class.java)
        } else {
            intent.getSerializableExtra(EXTRA_OPERATION)
        }

        abb.createButton.text = if (operation == Operation.CREATE) {
            "Criar"
        } else {
            "Atualizar"
        }

        if (receivedPerson != null) {
            abb.nameEditText.setText(receivedPerson.name)
            abb.spentEditText.setText(receivedPerson.spent.toString())
            abb.boughtEditText.setText(receivedPerson.bought.toString())
            abb.toPayTextView.text = receivedPerson.toPay.toString()
        }

        abb.createButton.setOnClickListener {
            if (operation == Operation.CREATE) {
                personController.create(
                    Person(
                        null,
                        abb.nameEditText.text.toString(),
                        abb.boughtEditText.text.toString(),
                        abb.spentEditText.text.toString().toDouble(),
                        0.0,
                    )
                ) { person ->
                    finish()
                }
            } else if (operation == Operation.EDIT) {
                if (receivedPerson != null) {
                    updatePerson(receivedPerson)
                }
            }

            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun updatePerson(receivedPerson: Person) {
//        (applicationContext as SplitTheBillApplication).getPersonRoom().getPersonDao()
//            .update(
//                Person(
//                    receivedPerson.id,
//                    abb.nameEditText.getText().toString(),
//                    abb.boughtEditText.text.toString(),
//                    abb.spentEditText.getText().toString().toDouble(),
//                    receivedPerson.toPay,
//                )
//            )
    }
}
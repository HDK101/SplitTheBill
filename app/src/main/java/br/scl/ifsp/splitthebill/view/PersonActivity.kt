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
import br.scl.ifsp.splitthebill.databinding.ActivityPersonBinding
import br.scl.ifsp.splitthebill.model.Person
import kotlinx.parcelize.Parcelize

class PersonActivity: AppCompatActivity() {
    enum class Operation {
        CREATE,
        EDIT
    }

    private val abb: ActivityPersonBinding by lazy { ActivityPersonBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(abb.root)

        val receivedPerson = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("walter", Person::class.java)
        }
        else {
            intent.getParcelableExtra("walter")
        }

        val operation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("operation", Operation::class.java)
        }
        else {
            intent.getSerializableExtra("operation")
        }

        if (operation == Operation.EDIT) {
            abb.updateButton.visibility = View.VISIBLE
            abb.deleteButton.visibility = View.VISIBLE
            abb.createButton.visibility = View.INVISIBLE
        }
        else {
            abb.updateButton.visibility = View.INVISIBLE
            abb.deleteButton.visibility = View.INVISIBLE
            abb.createButton.visibility = View.VISIBLE
        }

        if (receivedPerson != null) {
            abb.nameEditText.setText(receivedPerson.name)
            abb.spentEditText.setText(receivedPerson.spent.toString())
            abb.toPayTextView.text = receivedPerson.toPay.toString()
        }

        abb.createButton.setOnClickListener {
            (applicationContext as SplitTheBillApplication).getPersonRoom().getPersonDao().create(Person(
                null,
                abb.nameEditText.getText().toString(),
                abb.spentEditText.getText().toString().toDouble(),
                0.0,
            ))

            setResult(Activity.RESULT_OK)
            finish()
        }

        abb.updateButton.setOnClickListener {
            if (receivedPerson != null) {
                (applicationContext as SplitTheBillApplication).getPersonRoom().getPersonDao().update(Person(
                    receivedPerson.id,
                    abb.nameEditText.getText().toString(),
                    abb.spentEditText.getText().toString().toDouble(),
                    receivedPerson.toPay,
                ))
            }

            setResult(Activity.RESULT_OK)
            finish()
        }

        abb.deleteButton.setOnClickListener {
            if (receivedPerson != null) {
                (applicationContext as SplitTheBillApplication).getPersonRoom().getPersonDao().delete(receivedPerson)
            }

            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
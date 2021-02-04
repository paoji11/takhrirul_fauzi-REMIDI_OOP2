package com.example.crudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Antrian
import kotlinx.android.synthetic.main.activity_edit_antrian.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditAntrianActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var antrianId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_antrian)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_saveAntrian.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.AntrianDao().addAntrian(
                    Antrian(0, txt_nama.text.toString(), txt_alamat.text.toString(), Integer.parseInt(txt_no.text.toString()) )
                )
                finish()
            }
        }
        btn_updateAntrian.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.AntrianDao().updateAntrian(
                    Antrian(antrianId, txt_nama.text.toString(), txt_alamat.text.toString(), Integer.parseInt(txt_no.text.toString()) )
                )
                finish()
            }
        }
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_updateAntrian.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_saveAntrian.visibility = View.GONE
                btn_updateAntrian.visibility = View.GONE
                getAntrian()
            }
            Constant.TYPE_UPDATE -> {
                btn_saveAntrian.visibility = View.GONE
                getAntrian()
            }
        }
    }

    fun getAntrian() {
        antrianId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
           val antrians =  db.AntrianDao().getAntrian( antrianId )[0]
            txt_nama.setText( antrians.nama )
            txt_alamat.setText( antrians.alamat )
            txt_no.setText( antrians.no.toString() )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
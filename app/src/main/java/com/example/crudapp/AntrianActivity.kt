package com.example.crudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Antrian
import kotlinx.android.synthetic.main.activity_antrian.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AntrianActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var antrianAdapter: AntrianAdapter
    //menampilkan semua data //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_antrian)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadAntrian()
    }

    fun loadAntrian(){
        CoroutineScope(Dispatchers.IO).launch {
            val allAntrian = db.AntrianDao().getAllAntrian()
            Log.d("AntrianActivity", "dbResponse: $allAntrian")
            withContext(Dispatchers.Main) {
                antrianAdapter.setData(allAntrian)
            }
        }
    }

    fun setupListener() {
        btn_createAntrian.setOnClickListener {
           intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        antrianAdapter = AntrianAdapter(arrayListOf(), object: AntrianAdapter.OnAdapterListener {
            override fun onClick(antrian: Antrian) {
                // read detail
                intentEdit(antrian.id, Constant.TYPE_READ)
            }

            override fun onDelete(antrian: Antrian) {
                // delete data
                deleteDialog(antrian)
            }

            override fun onUpdate(antrian: Antrian) {
                // edit data
                intentEdit(antrian.id, Constant.TYPE_UPDATE)
            }

        })
        list_antrian.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = antrianAdapter
        }
    }

    fun intentEdit(siswaId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditAntrianActivity::class.java)
                .putExtra("intent_id", siswaId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(antrian: Antrian) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.AntrianDao().deleteAntrian(antrian)
                    loadAntrian()
                }
            }
        }
        alertDialog.show()
    }
}
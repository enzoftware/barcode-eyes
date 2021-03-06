package com.projects.enzoftware.barcodereader.fragments


import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.projects.enzoftware.barcodereader.R
import com.projects.enzoftware.barcodereader.adapter.RecyclerViewAdapter
import com.projects.enzoftware.barcodereader.db.BarcodeDao
import com.projects.enzoftware.barcodereader.db.BarcodeRoomDatabase
import com.projects.enzoftware.barcodereader.model.BarcodeEntity
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton


/**
 * A simple [Fragment] subclass.
 * Use the [ScannedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScannedFragment : Fragment() {

    private var barcode_entity_list : ArrayList<BarcodeEntity> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_scanned, container, false)
        val recycler = view!!.findViewById<RecyclerView>(R.id.recyclerViewBarcodes)
        val btnDeleteAll = view.findViewById<ImageButton>(R.id.delete_all)
        val barcodeDao : BarcodeDao = BarcodeRoomDatabase.getInstance(ctx).barcode()
        barcodeDao.getAllBarcodes().observe(this, Observer { barcodeEntity : List<BarcodeEntity>? ->
            printBarcodes(barcodeEntity as ArrayList<BarcodeEntity>,recycler,ctx)
        })
        printBarcodes(barcode_entity_list,recycler,ctx)
        btnDeleteAll.setOnClickListener {
            alert("Hey, estas seguro que quieres eliminar todos los registros? "){
                yesButton {
                    barcodeDao.cleanDB()
                    printBarcodes(barcode_entity_list,recycler,ctx)
                    toast("Registros eliminados")
                }
                noButton  {
                    toast("Ok :)")
                }
            }.show()
        }
        return view
    }

    private fun printBarcodes(list: ArrayList<BarcodeEntity>, recycler: RecyclerView, context: Context){
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.hasFixedSize()
        recycler.adapter = RecyclerViewAdapter(context,list)
    }

}
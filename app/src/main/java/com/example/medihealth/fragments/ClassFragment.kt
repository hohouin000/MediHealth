package com.example.medihealth.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.medihealth.Adapter.ClassAdapter
import com.example.medihealth.DataClass.Classes
import com.example.medihealth.R
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlin.collections.ArrayList

class ClassFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var classArrayList: ArrayList<Classes>
    private lateinit var tempArraylist: ArrayList<Classes>
    private lateinit var classAdapter: ClassAdapter
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_class, container, false)

        recyclerView = view.findViewById(R.id.ClassList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        classArrayList = arrayListOf()
        tempArraylist = arrayListOf()
        eventChangeListener()
        classAdapter = ClassAdapter(requireContext(), classArrayList)

        recyclerView.adapter = classAdapter

        return view
    }


    private fun eventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("class").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {

                        classArrayList.add(
                            dc.document.toObject(
                                (Classes::class.java)
                            )
                        )
                    }
                }
                tempArraylist.clear()
                tempArraylist.addAll(classArrayList)
                classAdapter.notifyDataSetChanged()
            }
        })
    }
}
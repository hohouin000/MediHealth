package com.example.medihealth.fragments

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
import androidx.appcompat.widget.SearchView;
import java.util.*

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
        this.setHasOptionsMenu(true)
        classAdapter = ClassAdapter(requireContext(), tempArraylist)

        recyclerView.adapter = classAdapter

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search,menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String?): Boolean {
                tempArraylist.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    classArrayList.forEach{
                        if (it.class_name.toString().toLowerCase(Locale.getDefault()).contains(searchText)){
                            tempArraylist.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                }else{
                    tempArraylist.clear()
                    tempArraylist.addAll(classArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArraylist.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    classArrayList.forEach{
                        if (it.class_name.toString().toLowerCase(Locale.getDefault()).contains(searchText)){
                            tempArraylist.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                }else{
                    tempArraylist.clear()
                    tempArraylist.addAll(classArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }


        })
        return super.onCreateOptionsMenu(menu,inflater)
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
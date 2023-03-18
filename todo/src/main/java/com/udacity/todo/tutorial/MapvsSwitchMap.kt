package com.udacity.todo.tutorial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 * map() --> When I just have to change a data type to another data type
 * switchMap() --> When you have to change an input LiveData to a different LiveData
 */

object ContactsRepository {
    private val contacts: MutableList<Contact> = mutableListOf(
        Contact(1, "John", "Doe"),
        Contact(2, "Billy", "Bob"),
        Contact(3, "Anton", "Potter")
    )

    fun getFilteredContacts(name: String): LiveData<List<Contact>> {
        return MutableLiveData(contacts.filter {
            it.firstName.contains(name) || it.lastName.contains(
                name
            )
        })
    }
}

data class Contact(val id: Int, val firstName: String, val lastName: String)
data class ContactUi(val id: Int, val name: String)

class ContactsViewModel : ViewModel() {
    val name: MutableLiveData<String> = MutableLiveData()

    private val contacts = Transformations.switchMap(name) { nameQuery ->
        ContactsRepository.getFilteredContacts(
            nameQuery
        )
    }

    fun getContactsMapFiltered(): LiveData<List<ContactUi>> {
        return Transformations.map(contacts) { contactList ->
            val newContacts: MutableList<ContactUi> = mutableListOf()
            contactList.forEach {
                newContacts.add(
                    ContactUi(
                        it.id,
                        "${it.firstName} ${it.lastName}"
                    )
                )
            }
            newContacts
        }
    }

}

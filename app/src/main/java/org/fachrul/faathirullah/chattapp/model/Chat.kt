package org.fachrul.faathirullah.chattapp.model

data class Chat(
    val sender: String? = null,
    var message: String? = null,
    var fireBaseKey: String? = null
){
    fun toMap(): Map<String, String>{
        return hashMapOf(
            "sender" to sender.orEmpty(),
            "message" to message.orEmpty()
        )
    }
}
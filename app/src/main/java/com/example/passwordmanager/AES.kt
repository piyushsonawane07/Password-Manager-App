package com.example.passwordmanager

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


class AES {

    companion object {
        const val passKey=""
    }

    @Throws(Exception::class)
    fun encrypt(input: String, password: String?): String {
        val key = generateKey(passKey) //Generation Key
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding") //creating object
        cipher.init(Cipher.ENCRYPT_MODE, key) //initialisation
        val encVal = cipher.doFinal(input.toByteArray(charset("UTF-8"))) //Getting the Encrypted value.
        return Base64.encodeToString(
            encVal,
            Base64.DEFAULT
        ) //converting the encrypted value to String and returning that encrypted value.

        //Base64-encode the given data and return a newly allocated String with the result.
        //It's basically a way of encoding arbitrary binary data in ASCII text. It takes 4 characters per 3 bytes of data,
        // plus potentially a bit of padding at the end.
        //Essentially each 6 bits of the input is encoded in a 64-character alphabet.
        //The "standard" alphabet uses A-Z, a-z, 0-9 and + and /, with = as a padding character. There are URL-safe variants.
    }

    @Throws(Exception::class)
    fun decrypt(data: String?, password_text: String): String {
        val key = generateKey(password_text) //Generation Key
        val c = Cipher.getInstance("AES/ECB/PKCS5Padding")
        c.init(Cipher.DECRYPT_MODE, key)
        val decodedvalue = Base64.decode(data, Base64.DEFAULT) // as we have encoded in base 64 so to decrypt we need to decode that
        val decvalue = c.doFinal(decodedvalue)//final decoding operation
        return String(decvalue, charset("UTF-8")) //converting the bytes into the string
    }

    @Throws(Exception::class)
    private fun generateKey(password: String): SecretKeySpec {
        val digest = MessageDigest.getInstance("SHA-256") //for using hash function SHA-256
        val bytes = password.toByteArray(charset("UTF-8"))
        digest.update(bytes, 0, bytes.size) //processes bytes array
        val key =
            digest.digest() //Completes the hash computation by performing final operations such as padding.
        return SecretKeySpec(key, "AES")
    }

}

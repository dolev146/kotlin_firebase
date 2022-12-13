fun updateProfile(){
    auth.currentUser?.let{user ->
        val username = etUsername.text.toString()
        val photoURI = Uri.parse("android.resource://$packageName/${R.drawable.ic_profile}")
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .setPhotoUri(photoURI)
            .build()

        CoroutineScope(Dispatchers.IO).launch{
            try {
                user.updateProfile(profileUpdates).await()
                // auth.currentUser?.updateProfile(profileUpdates)?.await()
                withContext(Dispatchers.Main){
                    checkLoggedInState(auth)
                    Toast.makeText(this@MainActivity, "Profile Updated", Toast.LENGTH_SHORT).show()
                }
            } 
            catch (e: Exception) {
                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
            
        }
    }
}


// write a function that check if user is logged in take in auth as a parameter and return a boolean
// if user is logged in return true else return false
fun checkLoggedInState(auth: FirebaseAuth): Boolean {
    val user = auth.currentUser
    if (user != null) {
        Log.d(MainActivity.TAG, "User is logged in")
        etUsername.setText(user.displayName)
        ivProfile.setImageURI(user.photoUrl)

        return true
    } else {
        Log.d(MainActivity.TAG, "User is not logged in")
        return false
    }
}

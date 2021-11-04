package com.example.aeoncompose.data.response

import android.graphics.drawable.Icon

class ResourceResponse(val name: String, val icon: Icon, val lang: String, val resources: ArrayList<ResourcesItem>)
class ResourcesItem(val key: String, val value: String)
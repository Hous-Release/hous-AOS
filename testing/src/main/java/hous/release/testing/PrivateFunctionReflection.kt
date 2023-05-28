package hous.release.testing

import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/*
* [Class].callPrivateFunc(`private 메서드명`)
* ex ) userInputViewModel.callPrivateFunc("selectedHashtags")
* 
* */
inline fun <reified T> T.callPrivateFunc(name: String, vararg args: Any?): Any? =
    T::class
        .declaredMemberFunctions
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.call(this, *args)

/*
* [Class].getPrivateProperty<Class, Type>("private property name")
* ex) profileEditViewModel.getPrivateProperty<ProfileEditViewModel, MutableStateFlow<User>>("cacheUser")
*
* */
inline fun <reified T : Any, R> T.getPrivateProperty(name: String): R? =
    T::class
        .memberProperties
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.get(this) as? R

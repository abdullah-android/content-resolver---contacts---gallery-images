package com.android.learningcontentproviders.common

@Suppress("NOTHING_TO_INLINE")
inline fun String.startsWithAlphabet(): Boolean {
    return !isNullOrEmpty()
            && first().isLetter()
            && (first().isUpperCase()
            || first().isLowerCase())
}
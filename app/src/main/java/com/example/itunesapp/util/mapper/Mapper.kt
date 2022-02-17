package com.example.itunesapp.util.mapper

@FunctionalInterface
interface Mapper<in T, out R> {
    suspend fun map(item: T): R
}
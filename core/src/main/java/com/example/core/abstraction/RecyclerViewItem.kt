package com.example.core.abstraction

import com.example.core.utility.DefaultDiffUtil
/**
 *
 * This is used for RecyclerView Items
 * Allows the use of a generic DiffUtil
 * @see DefaultDiffUtil
 *
 */
abstract class RecyclerViewItem {
    abstract val id: Number
}
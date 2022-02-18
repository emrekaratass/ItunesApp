package com.example.itunesapp.data.entity

import com.example.itunesapp.ui.entity.ArticleViewItem

object ArticleFactory {

    fun getArticle() = ArticleViewItem(
        artworkUrl100 = "https://is5-ssl.mzstatic.com/image/thumb/Video115/v4/cc/f1/52/ccf152a5-6149-282d-804e-50a44180f49c/pr_source.lsr/100x100bb.jpg",
        collectionName = "Harry Potter Collection",
        collectionPrice = 14.99,
        releaseDate = "2010-11-19T08:00:00Z",
        description = "Harry, Ron and Hermione set out on their perilous mission to track down and destroy the Horcruxes--the keys to Voldemort's immortality. On their own, without the guidance or protection of their professors, the three friends must now rely on one another more than ever. But there are Dark Forces in their midst that threaten to tear them apart. Meanwhile, the Wizarding world has become a dangerous place for all enemies of the Dark Lord. The long-feared war has begun and Voldemort's Death Eaters seize control of the Ministry of Magic and even Hogwarts, terrorizing and arresting anyone who might oppose them. But the one prize they still seek is the one most valuable to Voldemort: Harry Potter. Harry's only hope is to find the Horcruxes before Voldemort finds him. But as he searches for clues, he uncovers an old and almost forgotten tale--the legend of the Deathly Hallows.",
    )
}

/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.model.data

import com.google.gson.JsonObject

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, models for marvel api.
 */

/**
 * @desc defines comics top response.
 */
data class ModelComic(
    val attributionHTML: String,
    val attributionText: String,
    val code: String,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)

/**
 * @desc defines comics data wrapper.
 */
data class ComicDataWrapper(
    val attributionHTML: String,
    val attributionText: String,
    val code: String,
    val copyright: String,
    val data: JsonObject,
    val etag: String,
    val status: String
)

/**
 * @desc defines comics data response.
 */
data class Data(
    val count: String,
    val limit: String,
    val offset: String,
    val results: List<Comic>,
    val total: String
)

/**
 * @desc defines comics data container response.
 */
data class ComicDataContainer(
    val count: String,
    val limit: String,
    val offset: String,
    //val results: JsonArray,
    val results: List<Comic>,
    val total: String
)

/**
 * @desc defines comics data response.
 */
data class Comic(
    val characters: Characters,
    val collectedIssues: List<CollectedIssue>,
    val collections: List<Collection>,
    val creators: Creators,
    val dates: List<Date>,
    val description: String,
    val diamondCode: String,
    val digitalId: String,
    val ean: String,
    val events: Events,
    val format: String,
    val id: String,
    val images: List<Image>,
    val isbn: String,
    val issn: String,
    val issueNumber: String,
    val modified: String,
    val pageCount: String,
    val prices: List<Price>,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val textObjects: List<TextObject>,
    val thumbnail: Thumbnail,
    val title: String,
    val upc: String,
    val urls: List<Url>,
    val variantDescription: String,
    val variants: List<Variant>
)

/**
 * @desc defines comics character data response.
 */
data class Characters(
    val available: String,
    val collectionURI: String,
    val items: List<Item>,
    val returned: String
)

/**
 * @desc defines comics item data response.
 */
data class Item(
    val name: String,
    val resourceURI: String,
    val role: String
)

/**
 * @desc defines comics collected issues data response.
 */
data class CollectedIssue(
    val name: String,
    val resourceURI: String
)

/**
 * @desc defines comics collection data response.
 */
data class Collection(
    val name: String,
    val resourceURI: String
)

/**
 * @desc defines comics creators data response.
 */
data class Creators(
    val available: String,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: String
)

/**
 * @desc defines comics item x data response.
 */
data class ItemX(
    val name: String,
    val resourceURI: String,
    val role: String
)

/**
 * @desc defines comics date data response.
 */
data class Date(
    val date: String,
    val type: String
)

/**
 * @desc defines comics events data response.
 */
data class Events(
    val available: String,
    val collectionURI: String,
    val items: List<ItemXX>,
    val returned: String
)

/**
 * @desc defines comics item xx data response.
 */
data class ItemXX(
    val name: String,
    val resourceURI: String
)

/**
 * @desc defines comics image data response.
 */
data class Image(
    val extension: String,
    val path: String
)

/**
 * @desc defines comics price data response.
 */
data class Price(
    val price: String,
    val type: String
)

/**
 * @desc defines comics series data response.
 */
data class Series(
    val name: String,
    val resourceURI: String
)

/**
 * @desc defines comics stories data response.
 */
data class Stories(
    val available: String,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: String
)

/**
 * @desc defines comics item xxx data response.
 */
data class ItemXXX(
    val name: String,
    val resourceURI: String,
    val type: String
)

/**
 * @desc defines comics text object data response.
 */
data class TextObject(
    val language: String,
    val text: String,
    val type: String
)

/**
 * @desc defines comics thumbnail data response.
 */
data class Thumbnail(
    val extension: String,
    val path: String
)

/**
 * @desc defines comics url data response.
 */
data class Url(
    val type: String,
    val url: String
)

/**
 * @desc defines comics variant data response.
 */
data class Variant(
    val name: String,
    val resourceURI: String
)
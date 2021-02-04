/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

/*
 * 2020 MarvelDroid , Attribution Images belong to Marvel Entertainment
 * Source Timestamps exist under: https://github.com/PabloRosas17
 * Written by Jose Pablo Rosas  <pablo.rosas.0170@gmail.com>, January 2020
 */

package organization.fizzy.marveldroid.model

import organization.fizzy.marveldroid.model.data.Thumbnail

/**
 * @author, evolandlupiz
 * @date, 5/17/2020
 * @property, MarvelDroid
 * @purpose, defines comic.
 */

/**
 * @desc comic wrapper.
 */
data class ModelDroidComic(
    var cthumbnail: Thumbnail
    , var ctitle: String? = null
    , var cissueno: String? = null
    , var cdescription: String? = null
    , var cisbn: String? = null
    , var cupc: String? = null
    , var cpagecount: String? = null
    , var cdate: String? = null
    , var cprice: String? = null
    , var attribution: String? = null
)
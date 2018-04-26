package io.github.tonnyl.latticify.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 23/09/2017.
 *
 * A file object contains information about a file shared with a workspace.
 *
 * {
 * "id" : "F2147483862",
 * "created" : 1356032811,
 * "timestamp" : 1356032811,
 * "name" : "file.htm",
 * "title" : "My HTML file",
 * "mimetype" : "text\/plain",
 * "filetype" : "text",
 * "pretty_type": "Text",
 * "user" : "U2147483697",
 * "mode" : "hosted",
 * "editable" : true,
 * "is_external": false,
 * "external_type": "",
 * "username": "",
 * "size" : 12345,
 * "url_private": "https:\/\/slack.com\/files-pri\/T024BE7LD-F024BERPE\/1.png",
 * "url_private_download": "https:\/\/slack.com\/files-pri\/T024BE7LD-F024BERPE\/download\/1.png",
 * "thumb_64": "https:\/\/slack-files.com\/files-tmb\/T024BE7LD-F024BERPE-c66246\/1_64.png",
 * "thumb_80": "https:\/\/slack-files.com\/files-tmb\/T024BE7LD-F024BERPE-c66246\/1_80.png",
 * "thumb_360": "https:\/\/slack-files.com\/files-tmb\/T024BE7LD-F024BERPE-c66246\/1_360.png",
 * "thumb_360_gif": "https:\/\/slack-files.com\/files-tmb\/T024BE7LD-F024BERPE-c66246\/1_360.gif",
 * "thumb_360_w": 100,
 * "thumb_360_h": 100,
 * "thumb_480": "https:\/\/slack-files.com\/files-tmb\/T024BE7LD-F024BERPE-c66246\/1_480.png",
 * "thumb_480_w": 480,
 * "thumb_480_h": 480,
 * "thumb_160": "https:\/\/slack-files.com\/files-tmb\/T024BE7LD-F024BERPE-c66246\/1_160.png",
 * "permalink": "https:\/\/tinyspeck.slack.com\/files\/cal\/F024BERPE\/1.png",
 * "permalink_public" : "https:\/\/tinyspeck.slack.com\/T024BE7LD-F024BERPE-3f9216b62c",
 * "edit_link": "https:\/\/tinyspeck.slack.com\/files\/cal\/F024BERPE\/1.png/edit",
 * "preview": "&lt;!DOCTYPE html&gt;\n&lt;html&gt;\n&lt;meta charset='utf-8'&gt;",
 * "preview_highlight": "&lt;div class=\"sssh-code\"&gt;&lt;div class=\"sssh-line\"&gt;&lt;pre&gt;&lt;!DOCTYPE html...",
 * "lines": 123,
 * "lines_more": 118,
 * "is_public": true,
 * "public_url_shared": false,
 * "display_as_bot" : false,
 * "channels": ["C024BE7LT", ...],
 * "groups": ["G12345", ...],
 * "ims": ["D12345", ...],
 * "initial_comment": {...},
 * "num_stars": 7,
 * "is_starred": true,
 * "pinned_to": ["C024BE7LT", ...],
 * "reactions": [
 * {
 * "name": "astonished",
 * "count": 3,
 * "users": [ "U1", "U2", "U3" ]
 * },
 * {
 * "name": "facepalm",
 * "count": 1034,
 * "users": [ "U1", "U2", "U3", "U4", "U5" ]
 * }
 * ],
 * "comments_count": 1
 * }
 */
@Parcelize
@SuppressLint("ParcelCreator")
data class File(

        @SerializedName("id")
        val id: String,

        /**
         * The [created] property is a unix timestamp representing when the file was created.
         */
        @SerializedName("created")
        val created: Long,

        /**
         * The [timestamp] property is deprecated and is provided only for backwards compatibility with older clients.
         */
        @SerializedName("timestamp")
        val timestamp: Long,

        /**
         * The [name] parameter may be null for unnamed files.
         */
        @SerializedName("name")
        val name: String?,

        @SerializedName("title")
        val title: String,

        /**
         * The [mimeType] and [fileType] props do not have a 1-to-1 mapping,
         * as multiple different files types ('html', 'js', etc.) share the same mime type.
         */
        @SerializedName("mimetype")
        val mimeType: String,

        /**
         * The [mimeType] and [fileType] props do not have a 1-to-1 mapping,
         * as multiple different files types ('html', 'js', etc.) share the same mime type.
         *
         * Possible [fileType] values include, but are not limited to the following:
         *
         * Type	Description
         * auto	Auto Detect Type
         * text	Plain Text
         * applescript	AppleScript
         * boxnote	BoxNote
         * c	C
         * csharp	C#
         * cpp	C++
         * css	CSS
         * csv	CSV
         * clojure	Clojure
         * coffeescript	CoffeeScript
         * cfm	ColdFusion
         * d	D
         * dart	Dart
         * diff	Diff
         * dockerfile	Docker
         * erlang	Erlang
         * fsharp	F#
         * fortran	Fortran
         * go	Go
         * groovy	Groovy
         * html	HTML
         * handlebars	Handlebars
         * haskell	Haskell
         * haxe	Haxe
         * java	Java
         * javascript	JavaScript/JSON
         * kotlin	Kotlin
         * latex	LaTeX/sTeX
         * lisp	Lisp
         * lua	Lua
         * markdown	Markdown (raw)
         * matlab	MATLAB
         * mumps	MUMPS
         * ocaml	OCaml
         * objc	Objective-C
         * php	PHP
         * pascal	Pascal
         * perl	Perl
         * pig	Pig
         * post	Slack Post
         * powershell	PowerShell
         * puppet	Puppet
         * python	Python
         * r	R
         * ruby	Ruby
         * rust	Rust
         * sql	SQL
         * sass	Sass
         * scala	Scala
         * scheme	Scheme
         * shell	Shell
         * smalltalk	Smalltalk
         * swift	Swift
         * tsv	TSV
         * vb	VB.NET
         * vbscript	VBScript
         * velocity	Velocity
         * verilog	Verilog
         * xml	XML
         * yaml	YAML
         */
        @SerializedName("filetype")
        val fileType: String,

        /**
         * The [prettyType] property contains a human-readable version of the type.
         */
        @SerializedName("pretty_type")
        val prettyType: String,

        @SerializedName("user")
        val user: String,

        /**
         * The [mode] property contains one of hosted, external, snippet or post.
         */
        @SerializedName("mode")
        val mode: String,

        /**
         * The [editable] property indicates that files are stored in editable mode.
         */
        @SerializedName("editable")
        val editable: Boolean,

        /**
         *  The [isExternal] property indicates whether the master copy of a file is stored within the system or not.
         *  If the file [isExternal], then the url property will point to the externally-hosted master file.
         *  Further, the [externalType] property will indicate what kind of external file it is, e.g. dropbox or gdoc.
         */
        @SerializedName("is_external")
        val isExternal: Boolean,

        /**
         * The [externalType] property indicates what kind of external file it is, e.g. dropbox or gdoc.
         */
        @SerializedName("external_type")
        val externalType: String,

        @SerializedName("username")
        val username: String,

        /**
         * The [size] parameter is the filesize in bytes. Snippets are limited to a maximum file size of 1 megabyte.
         */
        @SerializedName("size")
        val size: Long,

        /**
         * The [urlPrivate] property points to a URL to the file contents.
         */
        @SerializedName("url_private")
        val urlPrivate: String,

        /**
         * Editable-mode files will also have a [urlPrivateDownload] parameter, which includes headers to force a browser download.
         */
        @SerializedName("url_private_download")
        val urlPrivateDownload: String,

        /**
         * If a thumbnail is available for the file, the URL to a 64x64 pixel will be returned as the [thumb64] prop.
         */
        @SerializedName("thumb_64")
        val thumb64: String?,

        /**
         * The [thumb80] prop (when present) contains the URL of an 80x80 thumb.
         * Unlike the 64px thumb, this size is guaranteed to be 80x80,
         * even when the source image was smaller (it's padded with transparent pixels).
         */
        @SerializedName("thumb_80")
        val thumb80: String?,

        /**
         * A variable sized thumb will be returned as [thumb360],
         * with its longest size no bigger than 360 (although it might be
         * smaller depending on the source size).
         * Dimensions for this thumb are returned in [thumb360W] and [thumb360H].
         */
        @SerializedName("thumb_360")
        val thumb360: String?,

        /**
         * In the case where the original image was an animated gif with dimensions greater than 360 pixels,
         * we also created an animated thumbnail and pass it as [thumb360Gif].
         */
        @SerializedName("thumb_360_gif")
        val thumb360Gif: String?,

        @SerializedName("thumb_360_w")
        val thumb360W: Int?,

        @SerializedName("thumb_360_h")
        val thumb360H: Int?,

        @SerializedName("thumb_480")
        val thumb480: String?,

        @SerializedName("thumb_480_w")
        val thumb480W: Int?,

        @SerializedName("thumb_480_h")
        val thumb480H: Int?,

        @SerializedName("thumb_160")
        val thumb160: String?,

        @SerializedName("thumb_720")
        val thumb720: String?,

        @SerializedName("thumb_720_w")
        val thumb720W: Int?,

        @SerializedName("thumb_720_h")
        val thumb720H: Int?,

        @SerializedName("thumb_800")
        val thumb800: String?,

        @SerializedName("thumb_800_w")
        val thumb800W: Int?,

        @SerializedName("thumb_800_h")
        val thumb800H: Int?,

        @SerializedName("thumb_960")
        val thumb960: String?,

        @SerializedName("thumb_960_w")
        val thumb960W: Int?,

        @SerializedName("thumb_960_h")
        val thumb960H: Int?,

        @SerializedName("thumb_1024")
        val thumb1024: String?,

        @SerializedName("thumb_1024_w")
        val thumb1024W: Int?,

        @SerializedName("thumb_1024_h")
        val thumb1024H: Int?,

        @SerializedName("image_exif_rotation")
        val imageExifRotation: Int?,

        @SerializedName("original_w")
        val originalW: Int?,

        @SerializedName("original_h")
        val originalH: Int?,

        /**
         * The [permalink] URL points to a single page for the file containing details,
         * comments and a download link.
         */
        @SerializedName("permalink")
        val permalink: String,

        /**
         * If the file is available to the public, a [permalinkPublic] URL points to the public file itself.
         */
        @SerializedName("permalink_public")
        val permalinkPublic: String,

        /**
         * The [editLink] is only present for posts and snippets and is the page at which the file can be edited.
         */
        @SerializedName("edit_link")
        val editLink: String?,

        /**
         * For posts, we also include a short plain-text [preview] than can be shown in place of a thumbnail.
         */
        @SerializedName("preview")
        val preview: String?,

        /**
         * For snippets, we include a simple [preview] of the contents (a few truncated lines of plaintext),
         * as well as a more complex syntax-highlighted preview ([previewHighlight]) in HTML.
         */
        @SerializedName("preview_highlight")
        val previewHighlight: String?,

        /**
         *  The total count of lines in the snippet if returned in [lines],
         *  while [linesMore] contains a count of lines not shown in the preview.
         */
        @SerializedName("lines")
        val lines: Long?,

        /**
         *  The total count of lines in the snippet if returned in [lines],
         *  while [linesMore] contains a count of lines not shown in the preview.
         */
        @SerializedName("lines_more")
        val linesMore: Long?,

        /**
         * If a file is public, the [isPublic] flag will be set.
         */
        @SerializedName("is_public")
        val isPublic: Boolean,

        /**
         * If a file's public URL has been shared, the [publicUrlShared] flag will be set.
         */
        @SerializedName("public_url_shared")
        val publicUrlShared: Boolean,

        @SerializedName("display_as_bot")
        val displayAsBot: Boolean,

        /**
         * The [channels] array contains the IDs of any channels into which the file is currently shared.
         */
        @SerializedName("channels")
        val channels: List<String>,

        /**
         *  The [groups] array is the same but for private groups.
         *  Groups are only returned if the caller is a member of that group.
         */
        @SerializedName("groups")
        val groups: List<String>,

        /**
         * the [ims] array is the same but for IM channels.
         * IMs are only returned if the caller is a member of that IM channel.
         */
        @SerializedName("ims")
        val ims: List<String>,

        /**
         * The [initialComment] will be a comment from the file uploader, and will only be set
         * when the uploader commented on the file at the time of upload.
         * Clients can use this to display the comment with the file when announcing new file uploads.
         * Use [commentsCount] to determine how many comments are attached to a file.
         */
        @SerializedName("initial_comment")
        val initialComment: FileComment?,

        /**
         * The [numStars] property contains the number of users who have starred this file.
         * It is not present if no users have starred it. The [isStarred] property is present and true
         * if the calling user has starred the file, else it is omitted.
         */
        @SerializedName("num_stars")
        val numStars: Int,

        @SerializedName("is_starred")
        var isStarred: Boolean,

        /**
         * The [pinnedTo] array contains the IDs of any channels to which the file is currently pinned.
         */
        @SerializedName("pinned_to")
        val pinnedTo: List<String>?,

        /**
         * The [reactions] property contains any reactions that have been added to the file
         * and gives information about the type of reaction, the total number of users who added
         * that reaction and a (possibly incomplete) list of users who have added that reaction to the file.
         * The users array in the [reactions] property might not always contain all users
         * that have reacted (we limit it to X users, and X might change),
         * however count will always represent the count of all users who made that reaction
         * (i.e. it may be greater than users.length).
         *
         * If the authenticated user has a given reaction then they are guaranteed to appear in the users array,
         * regardless of whether count is greater than users.length or not.
         */
        @SerializedName("reactions")
        val reactions: List<Reaction>?,

        @SerializedName("comments_count")
        val commentsCount: Int,

        @SerializedName("score")
        val score: String?,

        @SerializedName("top_file")
        val topFile: Boolean?

) : Parcelable
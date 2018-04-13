package io.github.tonnyl.latticify.util

/**
 * Created by lizhaotailang on 02/01/2018.
 */
class Constants {

    companion object {
        const val SHORTCUT_ID_SEARCH = "SHORTCUT_ID_SEARCH"
        const val INTENT_ACTION_SEARCH = "INTENT_ACTION_SEARCH"
        const val SHORTCUT_ID_SNOOZE = "SHORTCUT_ID_SNOOZE"
        const val INTENT_ACTION_SNOOZE = "INTENT_ACTION_SNOOZE"

        const val BROADCAST_EXTRA = "BROADCAST_EXTRA"

        // Broadcast intent filters
        const val FILTER_HELLO = "io.github.tonnyl.hello.broadcast"
        const val FILTER_USER_TYPING = "io.github.tonnyl.typing.broadcast"
        const val FILTER_MESSAGE = "io.github.tonnyl.message.broadcast"
        const val FILTER_GOODBYE = "io.github.tonnyl.goodbye.broadcast"
        const val FILTER_PIN_REMOVED = "io.github.tonnyl.pin.removed.broadcast"
        const val FILTER_PIN_ADDED = "io.github.tonnyl.pin.added.broadcast"
        const val FILTER_REACTION_ADDED = "io.github.tonnyl.reaction.added.broadcast"
        const val FILTER_REACTION_REMOVED = "io.github.tonnyl.reaction.removed.broadcast"
    }

}
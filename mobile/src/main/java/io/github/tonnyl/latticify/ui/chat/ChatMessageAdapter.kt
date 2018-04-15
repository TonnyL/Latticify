package io.github.tonnyl.latticify.ui.chat

import com.airbnb.epoxy.EpoxyAdapter
import com.airbnb.epoxy.EpoxyModel
import io.github.tonnyl.latticify.data.Message
import io.github.tonnyl.latticify.epoxy.LoadMoreModel_
import io.github.tonnyl.latticify.epoxy.MessageModel_

class ChatMessageAdapter : EpoxyAdapter() {

    private val mLoadMoreModel = LoadMoreModel_()

    init {
        enableDiffing()
    }

    public override fun addModel(modelToAdd: EpoxyModel<*>) {
        super.addModel(modelToAdd)
    }

    public override fun addModels(modelsToAdd: Array<EpoxyModel<*>>) {
        super.addModels(*modelsToAdd)
    }

    public override fun addModels(modelsToAdd: Collection<EpoxyModel<*>>) {
        super.addModels(modelsToAdd)
    }

    public override fun removeAllModels() {
        super.removeAllModels()
    }

    public override fun removeModel(model: EpoxyModel<*>) {
        super.removeModel(model)
    }

    fun showLoadingMore(loading: Boolean) {
        if (loading) {
            addModel(mLoadMoreModel)
        } else {
            removeModel(mLoadMoreModel)
        }
    }

    fun insertModel(modelToInsert: EpoxyModel<*>, position: Int) {
        models.add(position, modelToInsert)

        // The recycler view layout is reversed. So the position to notify is models' size instead 0.
        notifyItemInserted(models.size - 1)
    }

    fun updateModel(modelToUpdate: EpoxyModel<*>, message: Message) {
        if (modelToUpdate is MessageModel_) {
            models.firstOrNull {
                it is MessageModel_
                        && it.message.ts == modelToUpdate.message.ts
            }?.let {
                (it as MessageModel_).message.edited = message.edited
                notifyModelChanged(it)
            }
        }
    }

}
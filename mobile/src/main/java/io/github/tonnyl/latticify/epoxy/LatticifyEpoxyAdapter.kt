package io.github.tonnyl.latticify.epoxy

import com.airbnb.epoxy.EpoxyAdapter
import com.airbnb.epoxy.EpoxyModel

/**
 * Created by lizhaotailang on 24/09/2017.
 */
class LatticifyEpoxyAdapter : EpoxyAdapter() {

    private val mLoadMoreModel = LoadMoreModel_()

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

}

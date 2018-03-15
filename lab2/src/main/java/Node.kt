import guru.nidi.graphviz.model.LinkSource

data class  Node <T: Any> (val data: T, var left: Node<T>?, var right: Node<T>?, var parent: Node<T>?) {
    override fun toString(): String {
        return data.toString()
    }
}
class SplayTree<T : Comparable<T>> {
    var root: Node<T>? = null

    fun add(value: T): Node<T>? {
        if (root == null) {
            val newNode = Node(value, null, null, null)
            root = newNode
            return newNode
        }
        var current: Node<T>? = root
        while (current != null) {
            val compare = value.compareTo(current.data)
            if (compare == 0) {
                throw IllegalArgumentException("Element is exist")
            } else if (compare < 0) {
                val newNode = Node(value, null, null, current)
                if (current.right != null) {
                    current = current.right
                } else {
                    current.right = newNode
                    return newNode
                }
            } else if (compare > 0) {
                if (current.left != null) {
                    current = current.left
                } else {
                    val newNode = Node(value, null, null, current)
                    current.left = newNode
                    return newNode
                }
            }
        }
        return null
    }

    fun delete(value: T) = findAndDoSmth(value) { toDelete ->
        if (toDelete.left != null) {
            replaceNode(toDelete, handleLeft(toDelete))
        } else if (toDelete.right != null) {
            replaceNode(toDelete, handleRight(toDelete))
        } else {
            replaceNode(toDelete, null)
        }
        true
    }

    fun find(value: T): Boolean {
        val founded = findAndDoSmth(value) { it }
        return founded != null
    }

    private fun handleLeft(node: Node<T>): Node<T> {//найти замену представленной ноды по её левому ребёнку
        val left = node.left!!
        var preCur: Node<T>? = node
        var cur: Node<T> = left
        if(cur.right==null){
            preCur?.left = cur.left
            cur.left = null
        }else {
            while (cur.right != null) {
                preCur = cur
                cur = cur.right!!
            }
            preCur?.right = cur.left //правых нет 100%
            cur.left = null
        }
        return cur
    }

    private fun handleRight(node: Node<T>): Node<T> {//найти замену представленной ноды по её правому ребёнку
        val right = node.right!!
        var preCur: Node<T>? = node
        var cur: Node<T> = right
        if(cur.left==null){
            preCur?.right = cur.right
            cur.right = null
        }else {
            while (cur.left != null) {
                preCur = cur
                cur = cur.left!!
            }
            preCur?.left = cur.right
            cur.right = null
        }
        return cur
    }

    private fun replaceNode(node: Node<T>, newNode: Node<T>?) {//заменить одну ноду другой
        val parent = node.parent
        if (parent == null) {
            root = newNode
        }else {
            if (node.equals(parent.left)) {
                parent.left = newNode
            } else if (node.equals(parent.right)) {
                parent.right = newNode
            } else {
                throw Exception("Parent not contained his child ¯\\_(ツ)_/¯")
            }
        }
        node.right?.parent = newNode
        node.left?.parent = newNode
        newNode?.left = node.left
        newNode?.right = node.right
        newNode?.parent = parent
        node.left = null
        node.right = null
    }

    private fun <F> findAndDoSmth(value: T, doSmth: (Node<T>) -> F): F? {
        var current: Node<T>? = root ?: return null
        while (current != null) {
            val compare = value.compareTo(current.data)
            if (compare == 0) {
                return doSmth(current)
            } else if (compare < 0) {
                current = current.right
            } else if (compare > 0) {
                current = current.left
            }
        }
        return null
    }

    fun <F> forEach(handle: (Node<T>) -> F) = recursive(root, handle)

    private fun <F> recursive(node: Node<T>?, handle: (Node<T>) -> F) {
        node ?: return
        recursive(node.right, handle)
        handle(node)
        recursive(node.left, handle)
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.appendln("Tree items:")
        forEach {
            builder.appendln(it.data.toString())
        }
        return builder.toString()
    }

}
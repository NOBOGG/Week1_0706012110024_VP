package Interface

interface CardListener {

    fun onCardClick(position:Int)
    fun onCardClick(position: Boolean, adapterPosition: Int)


}
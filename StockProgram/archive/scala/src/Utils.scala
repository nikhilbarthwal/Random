object Utils
{
  def Chop(s:String):String =
  {
    val x = s.trim()
    val l = x.length()
    if (l == 0) return ""

    def Check(c:Char) = (x.charAt(0) == '\'') || (x.charAt(0) == '\"')

    val n1 = if (Check(x.charAt(0))) 1 else 0
    val n2 = if (Check(x.charAt(l-1))) l-1 else l

    x.substring(n1,n2)
  }

  def Demonitize(x:String):String =
    if (x.charAt(0) == '$')
      x.substring(1)
    else if ((x.charAt(0) == '-') && (x.charAt(1) == '$'))
        "-" + x.substring(2)
    else x

  def Convert2Float(s:String):Float = Demonitize(Chop(s)).toFloat
  def Convert2Int(s:String):Int = Demonitize(Chop(s)).toInt

  def FilterOptions[T](l:List[Option[T]]):List[T] =
    l.foldLeft(List[T]()) { (s: List[T], x: Option[T]) =>
      x match {
        case Some(z) => z :: s
        case None => s
      }
    }.reverse
}

class MyList[T](msg:String)
{
  var z:List[T] = List()
  var lock:Boolean = false

  def Add(x:T):Unit =
  {
    if (lock) throw new Exception("Writing to locked list :" + msg)
    z=x::z
  }

  def Value:List[T] =
  {
    lock = true
    z.reverse
  }
}

class MyMap[K,V](msg:String)
{
  var z:scala.collection.mutable.Map[K,V] = scala.collection.mutable.Map()
  var lock:Boolean = false

  def Add(k:K, v:V):Unit =
  {
    if (lock) throw new Exception("Writing to locked map :" + msg)
    z(k) = v
  }

  def Value:Map[K,V] =
  {
    lock = true
    z.toMap
  }
}

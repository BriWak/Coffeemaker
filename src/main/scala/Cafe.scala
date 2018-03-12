import scala.concurrent.Future
import scala.util.Random
import scala.util.control.NonFatal
import scala.concurrent.ExecutionContext.Implicits.global

object Cafe extends App {

  type CoffeeBeans = String
  type Groundcoffee = String

  case class Water(temperature: Int)

  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String

  case class GrindingException(msg: String) extends Exception(msg)


  def grind(beans: CoffeeBeans): Future[Groundcoffee] = Future {
    println("start grinding...")
    //Thread.sleep(Random.nextInt(2000))
    if (beans == "baked beans") throw GrindingException("are you joking?")
    println("finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future {
    println("heating the water now")
    //Thread.sleep(Random.nextInt(2000))
    println("hot, it's hot!")
    water.copy(temperature = 85)
  }

  def frothedMilk(milk: Milk): Future[FrothedMilk] = Future {
    println("milk frothing system engaged!")
    //Thread.sleep(Random.nextInt(2000))
    print("shutting down milk frothing system")
    s"frothed $milk"
  }

  def brew(coffee: Groundcoffee, heatedWater: Water): Future[Espresso] = Future {
    println("happy brewing :)")
    //Thread.sleep(Random.nextInt(2000))
    println("it's brewed!")
    "espresso"
  }

  def combine(espresso: Espresso, frothedMilk: FrothedMilk): Cappuccino = "cappuccino"


  def prepareCappuccino(): Future[Cappuccino] = {
    val groundCoffee = grind("arabica beans")
    val heatedWater = heatWater(Water(82))
    val frotherMilk = frothedMilk("wholemilk")

    for {
      ground <- groundCoffee
      water <- heatedWater
      foam <- frotherMilk
      espresso <- brew(ground, water)
    } yield {
      combine(foam, espresso)
    }
  }


  Cafe.prepareCappuccino map {
    c => println(c)
  } recover {
    case NonFatal(e) => println(s"Didn't make cappuccino $e")
  }

}
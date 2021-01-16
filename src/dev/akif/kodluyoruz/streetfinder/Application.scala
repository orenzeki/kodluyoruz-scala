package dev.akif.kodluyoruz.streetfinder
import java.io.File
/**
 * See CSV file at: https://github.com/makiftutuncu/kodluyoruz-scala/blob/main/data/streets.csv
 *
 * Original data: https://github.com/life/il-ilce-mahalle-sokak-cadde-sql
 */
object Application {
  def main(args: Array[String]): Unit = { 
    val newCsvLoader = new CsvLoader {
      override def loadCsv(file: File): List[String] = io.Source.fromFile(file).getLines.map(_.split(",")(1)).toList
    };
    
    val streets = new StreetFinder() {
      override def findStreets(streets: List[String], names: Set[String]): List[String] = {
          val list = streets.collect{
            case street if names.exists(key => street.contains(key)) =>
              street
          }
          list.distinct
      }
    };
    
    val street_file = new File("data/streets.csv");
    val streetSet = streets.findStreets(newCsvLoader.loadCsv(street_file), Set("GÜL", "PAPATYA", "MENEKŞE"));
    
    println(s"\nNumber of matched values: ${streetSet.size}\n")
    println(s"Matched Values: \n")
    streetSet.foreach { i =>
      println(i)
    }
    
  }
}

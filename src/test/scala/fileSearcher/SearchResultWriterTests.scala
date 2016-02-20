package fileSearcher

import java.nio.file.Files
import java.io.File
import scala.io.Source

import org.scalatest.FlatSpec

class SearchResultWriterTests  extends FlatSpec{

  def randomFilePath: String = s"src/test/resources/tmp/${System.currentTimeMillis() / 1000L}.dat"

  "SearchResultWriter when results passed" should
  "save them to a file in a filesystem" in {
    val fileName = randomFilePath
    val results = List(("readme.txt", Some(3)), ("license.txt", None))
    val writer  = SearchResultWriter
    writer.writeToFile(fileName, results)

    val file = new File(fileName)
    val fileSource = Source.fromFile(file)
    val lines = fileSource.getLines().toList

    try {
      assert(lines == List("readme.txt:3", "license.txt"))
    } finally {
      fileSource.close()
      Files.delete(file.toPath)
    }
  }
}

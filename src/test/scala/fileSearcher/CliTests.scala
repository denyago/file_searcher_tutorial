package fileSearcher

import org.scalatest.FlatSpec

class CliTests extends FlatSpec{

  private def matcherFromCliRunner(args: Array[String]) = Cli.argsToMather(args)

  "CliRunner when one argument passed" should
  "return FilterChecker with filter" in {
    val args   = Array("passwd")
    assert(matcherFromCliRunner(args) == new Matcher("passwd"))
  }

  "CliRunner when two arguments passed" should
    "return FilterChecker with filter and root path" in {
    val args   = Array("passwd", "/tmp")
    assert(matcherFromCliRunner(args) == new Matcher("passwd", "/tmp"))
  }

  "CliRunner when three arguments passed" should
    "return FilterChecker with filter, root path and content filter" in {
    val args   = Array("passwd", "/tmp", "root")
    assert(matcherFromCliRunner(args) == new Matcher("passwd", "/tmp", Option("root")))
  }

  "CliRunner when four arguments passed" should
    "return FilterChecker with filter, root path and content filter" in {
    val mBytes = 5
    val args   = Array("passwd", "/tmp", "root", mBytes.toString)
    assert(matcherFromCliRunner(args) == new Matcher("passwd", "/tmp", Option("root"), mBytes))
  }

  "CliRunner when more then four arguments passed" should
    "return FilterChecker with filter, root path and content filter" in {
    val mBytes = 5
    val args   = Array("passwd", "/tmp", "root", mBytes.toString, "bar", "xyz")
    assert(matcherFromCliRunner(args) == new Matcher("passwd", "/tmp", Option("root"), mBytes))
  }
}

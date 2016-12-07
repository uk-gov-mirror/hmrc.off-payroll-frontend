package uk.gov.hmrc.offpayroll

import uk.gov.hmrc.play.test.{UnitSpec}

/**
  * Created by peter on 05/12/2016.
  */
class WebflowTest extends UnitSpec {

  private val webflow = OffPayrollWebflow

  private val firstElement: Element = webflow.PersonalService.clusterElements(0)
  private val lastElement = webflow.PersonalService.clusterElements(8)


  "The Personal Service Section " should {
    "exist" in {
      assert(webflow.PersonalService != null, "Check we can get the main Personal Service Cluster")
    }
  }


  "Get start element" should {
    "return the start element " in {
      assert(webflow.getStart() === firstElement)
    }

    "Get next element based on the current element" should {
      "return the next valid object" in {
        val next = webflow.getNext(firstElement)
        assert(next.nonEmpty)
        assert(next.head == webflow.PersonalService.clusterElements(1))
      }
    }

    "Get next element should be empty as it is out of bounds" should {
      "return the next valid object" in {
        assert(webflow.getNext(lastElement).isEmpty)
      }
    }

    "Get element by Id" should {
      "return empty option" in {
        assert(webflow.getEelmentById(1, 0).isEmpty)
        assert(webflow.getEelmentById(0, lastElement.order + 1).isEmpty)
      }
    }

    "Get element by Id" should {
      "return a populated option" in {
        assert(webflow.getEelmentById(0, 0).nonEmpty)
        assert(webflow.getEelmentById(0, lastElement.order).nonEmpty)
      }
    }
  }

}
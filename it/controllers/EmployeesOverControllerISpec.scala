package controllers

import helpers.{CreateRequestHelper, IntegrationSpecBase, TestData}
import play.api.http.Status
import play.api.libs.ws.WSCookie

class EmployeesOverControllerISpec extends IntegrationSpecBase with CreateRequestHelper with Status with TestData{

  var cookies: Seq[WSCookie] = Nil

  s"Post or Get to /private-sector-employees" should {

    "Get sessionheaders successfully" in {

      lazy val sessionResult = getRequest("/disclaimer", true)

      whenReady(sessionResult) { result =>
        cookies = result.cookies
      }

    }


    "Return a 200 on successful get and should be on relevant page" in {

      lazy val res = getSessionRequest("/private-sector-employees", cookies,true)
      whenReady(res) { result =>
         result.status shouldBe OK
        result.body should include ("Have you already started this particular engagement for the end client?")
      }

    }

    "Return a 404 on a post to unused method" in {

      lazy val res = optionsRequest("/private-sector-employees")

      whenReady(res) { result =>
        result.status shouldBe NOT_FOUND
      }

    }

    "Return a 400 on unsuccessful post and stay on the same page" in {

      lazy val res = postSessionRequest("/private-sector-employees",defaultValue, cookies)

      whenReady(res) { result =>
        result.status shouldBe BAD_REQUEST
        result.body should include ("Have you already started this particular engagement for the end client?")

      }
    }

    "Return a 200 on Successful post and move onto next page" in {

      lazy val res = postSessionRequest("/private-sector-employees",aboutYouValue, cookies)

      whenReady(res) { result =>
        result.status shouldBe OK
        result.body should include ("Are you trading through a limited company, partnership or unincorporated body?")
      }

    }

  }

  s"Post or Get to /private-sector-employees/change" should {

    "Get sessionheaders successfully" in {

      lazy val sessionResult = getRequest("/disclaimer", true)

      whenReady(sessionResult) { result =>
        cookies = result.cookies
      }

    }


    "Return a 200 on successful get and should be on relevant page" in {

      lazy val res = getSessionRequest("/private-sector-employees/change", cookies,true)
      whenReady(res) { result =>
        result.status shouldBe OK
        result.body should include ("Have you already started this particular engagement for the end client?")
      }

    }

    "Return a 404 on a post to unused method" in {

      lazy val res = optionsRequest("/private-sector-employees/change")

      whenReady(res) { result =>
        result.status shouldBe NOT_FOUND
      }

    }

    "Return a 400 on unsuccessful post and stay on the same page" in {

      lazy val res = postSessionRequest("/private-sector-employees/change",defaultValue, cookies)

      whenReady(res) { result =>
        result.status shouldBe BAD_REQUEST
        result.body should include ("Have you already started this particular engagement for the end client?")

      }
    }

    "Return a 200 on Successful post and move onto next page" in {

      lazy val res = postSessionRequest("/private-sector-employees/change",aboutYouValue, cookies)

      whenReady(res) { result =>
        result.status shouldBe OK
        result.body should include ("Are you trading through a limited company, partnership or unincorporated body?")
      }

    }

  }


}

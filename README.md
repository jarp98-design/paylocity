UI framework: Selenium, TestNG, Maven
API framework: RestAssured

Pre-reqs:
Maven is needed to run both kind of tests: the UI and API. 

The test suites to be run can be found in the testng.xml file under the resources directory
All tests (UI & API) run with the same command
You can look at the test results by opening the index.html report under the target\surefire-reports directory
It is expected that some tests failed becuase of the application bugs
For the UI tests it is expected that the delete and edit employee to be skipped due these both tests depends on successfully creating an employee which is not happening because of the bug and we use the same employee data to update and delete. This can be changed by using an existing or other employee.

Command to execute the test: mvn clean test
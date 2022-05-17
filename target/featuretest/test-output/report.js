$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("Partner.feature");
formatter.feature({
  "line": 2,
  "name": "Partner_Login",
  "description": "",
  "id": "partner-login",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@DryRun"
    }
  ]
});
formatter.before({
  "duration": 4562502600,
  "status": "passed"
});
formatter.scenario({
  "line": 5,
  "name": "Login into Unity Dashboard",
  "description": "",
  "id": "partner-login;login-into-unity-dashboard",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 4,
      "name": "@Login1"
    }
  ]
});
formatter.step({
  "line": 6,
  "name": "I Navigate to Partner Page",
  "keyword": "Given "
});
formatter.step({
  "line": 7,
  "name": "I login to application with USER1_VALUE, PASSWORD1_VALUE",
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "I logout from the application",
  "keyword": "And "
});
formatter.match({
  "arguments": [
    {
      "val": "Partner",
      "offset": 14
    }
  ],
  "location": "StepDefs.navigateToUrl(String)"
});
formatter.result({
  "duration": 5433964100,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "USER1_VALUE",
      "offset": 28
    },
    {
      "val": " PASSWORD1_VALUE",
      "offset": 40
    }
  ],
  "location": "StepDefs.login(String,String)"
});
formatter.result({
  "duration": 1689695800,
  "status": "passed"
});
formatter.match({
  "location": "StepDefs.logout()"
});
formatter.result({
  "duration": 1295931800,
  "status": "passed"
});
formatter.after({
  "duration": 1206667900,
  "status": "passed"
});
formatter.scenarioOutline({
  "line": 11,
  "name": "Login into Unity Dashboard \u003cIndex\u003e@\u003cuserName\u003e",
  "description": "",
  "id": "partner-login;login-into-unity-dashboard-\u003cindex\u003e@\u003cusername\u003e",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 10,
      "name": "@Login2"
    }
  ]
});
formatter.step({
  "line": 12,
  "name": "I Navigate to Partner Page",
  "keyword": "Given "
});
formatter.step({
  "line": 13,
  "name": "I login to application with \u003cuserName\u003e, \u003cpassword\u003e",
  "keyword": "When "
});
formatter.step({
  "line": 14,
  "name": "I logout from the application",
  "keyword": "And "
});
formatter.examples({
  "line": 16,
  "name": "",
  "description": "",
  "id": "partner-login;login-into-unity-dashboard-\u003cindex\u003e@\u003cusername\u003e;",
  "rows": [
    {
      "cells": [
        "Index",
        "userName",
        "password"
      ],
      "line": 17,
      "id": "partner-login;login-into-unity-dashboard-\u003cindex\u003e@\u003cusername\u003e;;1"
    },
    {
      "cells": [
        "1",
        "test4@test.com",
        "DGdaXVpva"
      ],
      "line": 18,
      "id": "partner-login;login-into-unity-dashboard-\u003cindex\u003e@\u003cusername\u003e;;2"
    },
    {
      "cells": [
        "2",
        "test4@test.com",
        "DGdaXVpva"
      ],
      "line": 19,
      "id": "partner-login;login-into-unity-dashboard-\u003cindex\u003e@\u003cusername\u003e;;3"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 1780733600,
  "status": "passed"
});
formatter.scenario({
  "line": 18,
  "name": "Login into Unity Dashboard 1@test4@test.com",
  "description": "",
  "id": "partner-login;login-into-unity-dashboard-\u003cindex\u003e@\u003cusername\u003e;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 10,
      "name": "@Login2"
    },
    {
      "line": 1,
      "name": "@DryRun"
    }
  ]
});
formatter.step({
  "line": 12,
  "name": "I Navigate to Partner Page",
  "keyword": "Given "
});
formatter.step({
  "line": 13,
  "name": "I login to application with test4@test.com, DGdaXVpva",
  "matchedColumns": [
    1,
    2
  ],
  "keyword": "When "
});
formatter.step({
  "line": 14,
  "name": "I logout from the application",
  "keyword": "And "
});
formatter.match({
  "arguments": [
    {
      "val": "Partner",
      "offset": 14
    }
  ],
  "location": "StepDefs.navigateToUrl(String)"
});
formatter.result({
  "duration": 5255435900,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "test4@test.com",
      "offset": 28
    },
    {
      "val": " DGdaXVpva",
      "offset": 43
    }
  ],
  "location": "StepDefs.login(String,String)"
});
formatter.result({
  "duration": 1735740400,
  "status": "passed"
});
formatter.match({
  "location": "StepDefs.logout()"
});
formatter.result({
  "duration": 1281310200,
  "status": "passed"
});
formatter.after({
  "duration": 982104400,
  "status": "passed"
});
formatter.before({
  "duration": 1694003300,
  "status": "passed"
});
formatter.scenario({
  "line": 19,
  "name": "Login into Unity Dashboard 2@test4@test.com",
  "description": "",
  "id": "partner-login;login-into-unity-dashboard-\u003cindex\u003e@\u003cusername\u003e;;3",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 10,
      "name": "@Login2"
    },
    {
      "line": 1,
      "name": "@DryRun"
    }
  ]
});
formatter.step({
  "line": 12,
  "name": "I Navigate to Partner Page",
  "keyword": "Given "
});
formatter.step({
  "line": 13,
  "name": "I login to application with test4@test.com, DGdaXVpva",
  "matchedColumns": [
    1,
    2
  ],
  "keyword": "When "
});
formatter.step({
  "line": 14,
  "name": "I logout from the application",
  "keyword": "And "
});
formatter.match({
  "arguments": [
    {
      "val": "Partner",
      "offset": 14
    }
  ],
  "location": "StepDefs.navigateToUrl(String)"
});
formatter.result({
  "duration": 4972487000,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "test4@test.com",
      "offset": 28
    },
    {
      "val": " DGdaXVpva",
      "offset": 43
    }
  ],
  "location": "StepDefs.login(String,String)"
});
formatter.result({
  "duration": 1689360500,
  "status": "passed"
});
formatter.match({
  "location": "StepDefs.logout()"
});
formatter.result({
  "duration": 1428241100,
  "status": "passed"
});
formatter.after({
  "duration": 939694800,
  "status": "passed"
});
});
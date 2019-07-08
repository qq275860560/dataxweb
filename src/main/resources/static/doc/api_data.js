define({ "api": [
  {
    "type": "POST",
    "url": "/api/github/qq275860560/job/checkJob",
    "title": "校验唯一性",
    "group": "JobController",
    "name": "checkJob",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>校验唯一性，成功code返回200并且data返回true </p> <p><font color=\"red\">适用具体场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-任务管理管理-新建-校验合法性</font></li></p> <p><li><font color=\"red\">数据交换组件-任务管理管理-编辑-校验合法性</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型,确保参数urlencode之后才发送</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "defaultValue": "application/json;charset=UTF-8",
            "description": "<p>响应类型</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>&quot;Bearer &quot;串接调用/login接口获取的令牌</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求头部示例: ",
          "content": "{ \n\t\t\"Content-Type\":\"application/x-www-form-urlencoded\", \n     \"Accept\":\"application/json;charset=UTF-8\",\n     \"Authorization\":\"Bearer XXX\" \n}",
          "type": "json"
        }
      ]
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>任务,新建时为空，编辑时必填，</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>要校验唯一性的名称，必填</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=2&name=name2",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -X POST 'http://127.0.0.1:8080/api/github/qq275860560/job/checkJob' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "int",
            "optional": false,
            "field": "code",
            "description": "<p>状态码:{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息，校验结果的提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Boolean",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象,code为200时此字段有效{true:合法,false:不合法}</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回(校验成功时): ",
          "content": "{\"code\":200,\"msg\":\"名称有效\",\"data\":true}",
          "type": "json"
        },
        {
          "title": "成功返回(校验失败时):",
          "content": "{\"code\":200,\"msg\":\"名称已存在\",\"data\":false}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"名称必填\",\"data\":null}",
          "type": "json"
        },
        {
          "title": "失败返回: ",
          "content": "{\"code\":401,\"msg\":\"token已过期\",\"data\":null}",
          "type": "json"
        },
        {
          "title": "失败返回: ",
          "content": "{\"code\":403,\"msg\":\"用户无权限访问该接口\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "sampleRequest": [
      {
        "url": "/api/github/qq275860560/job/checkJob"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/JobController.java",
    "groupTitle": "JobController"
  }
] });

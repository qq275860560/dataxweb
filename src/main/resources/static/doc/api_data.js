define({ "api": [
  {
    "type": "POST",
    "url": "/api/build/getBuild",
    "title": "获取构建日志详情",
    "group": "BuildController",
    "name": "getBuild",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>获取构建日志详情，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-构建日志管理-编辑</font></li></p> <p><li><font color=\"red\">数据交换组件-构建日志管理-详情</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/build/getBuild?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象</p>"
          }
        ],
        "data对象字段数据:": [
          {
            "group": "data对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Id</p>"
          },
          {
            "group": "data对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":{\"id\":\"XXX\",\"name\":\"XXX\"}}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/build/getBuild"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/BuildController.java",
    "groupTitle": "构建日志接口"
  },
  {
    "type": "POST",
    "url": "/api/build/pageBuild",
    "title": "分页搜索构建日志",
    "group": "BuildController",
    "name": "pageBuild",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>分页搜索构建日志，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-构建日志管理</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "field": "name",
            "description": "<p>任务名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageNum",
            "description": "<p>查询页码，从1开始计算</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页展示的条数</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "pageNum=1&pageSize=10",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/build/pageBuild?pageNum=1&pageSize=10' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象</p>"
          }
        ],
        "data对象字段数据:": [
          {
            "group": "data对象字段数据:",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>记录总数，前端可以根据此值和pageSize，pageNum计算其他分页参数</p>"
          },
          {
            "group": "data对象字段数据:",
            "type": "Object[]",
            "optional": false,
            "field": "pageList",
            "description": "<p>数组</p>"
          }
        ],
        "pageList数组每个对象字段数据:": [
          {
            "group": "pageList数组每个对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Id</p>"
          },
          {
            "group": "pageList数组每个对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":{\"total\":100,\"pageList\":[{\"id\":\"XXX\",\"name\":\"XXX\"}]}}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/build/pageBuild"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/BuildController.java",
    "groupTitle": "构建日志接口"
  },
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "./src/main/resources/static/doc/main.js",
    "group": "D__workspace_git_github_qq275860560_dataxweb_src_main_resources_static_doc_main_js",
    "groupTitle": "D__workspace_git_github_qq275860560_dataxweb_src_main_resources_static_doc_main_js",
    "name": ""
  },
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "./target/classes/static/doc/main.js",
    "group": "D__workspace_git_github_qq275860560_dataxweb_target_classes_static_doc_main_js",
    "groupTitle": "D__workspace_git_github_qq275860560_dataxweb_target_classes_static_doc_main_js",
    "name": ""
  },
  {
    "type": "POST",
    "url": "/api/input/checkInput",
    "title": "校验唯一性",
    "group": "InputController",
    "name": "checkInput",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>校验唯一性，成功code返回200 </p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-输入流管理-新建-校验合法性</font></li></p> <p><li><font color=\"red\">数据交换组件-输入流管理-编辑-校验合法性</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID,新建时为空，编辑时必填，</p>"
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
        "content": "curl -i -X POST 'http://localhost:8045/api/input/checkInput' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码:{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Boolean",
            "optional": false,
            "field": "data",
            "description": "<p>校验结果{true:合法,false:不合法}</p>"
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
        "url": "/api/input/checkInput"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/InputController.java",
    "groupTitle": "输入流接口"
  },
  {
    "type": "POST",
    "url": "/api/input/deleteInput",
    "title": "删除输入流",
    "group": "InputController",
    "name": "deleteInput",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>删除输入流，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-输入流管理-删除</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/input/deleteInput?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/input/deleteInput"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/InputController.java",
    "groupTitle": "输入流接口"
  },
  {
    "type": "POST",
    "url": "/api/input/getInput",
    "title": "获取输入流详情",
    "group": "InputController",
    "name": "getInput",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>获取输入流详情，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-输入流管理-编辑</font></li></p> <p><li><font color=\"red\">数据交换组件-输入流管理-详情</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/input/getInput?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象</p>"
          }
        ],
        "data对象字段数据:": [
          {
            "group": "data对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Id</p>"
          },
          {
            "group": "data对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":{\"id\":\"XXX\",\"name\":\"XXX\"}}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/input/getInput"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/InputController.java",
    "groupTitle": "输入流接口"
  },
  {
    "type": "POST",
    "url": "/api/input/pageInput",
    "title": "分页搜索输入流",
    "group": "InputController",
    "name": "pageInput",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>分页搜索输入流，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-输入流管理</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "field": "name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageNum",
            "description": "<p>查询页码，从1开始计算</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页展示的条数</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "pageNum=1&pageSize=10",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/input/pageInput?pageNum=1&pageSize=10' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象</p>"
          }
        ],
        "data对象字段数据:": [
          {
            "group": "data对象字段数据:",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>记录总数，前端可以根据此值和pageSize，pageNum计算其他分页参数</p>"
          },
          {
            "group": "data对象字段数据:",
            "type": "Object[]",
            "optional": false,
            "field": "pageList",
            "description": "<p>数组</p>"
          }
        ],
        "pageList数组每个对象字段数据:": [
          {
            "group": "pageList数组每个对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Id</p>"
          },
          {
            "group": "pageList数组每个对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":{\"total\":100,\"pageList\":[{\"id\":\"XXX\",\"name\":\"XXX\"}]}}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/input/pageInput"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/InputController.java",
    "groupTitle": "输入流接口"
  },
  {
    "type": "POST",
    "url": "/api/input/saveInput",
    "title": "保存输入流",
    "group": "InputController",
    "name": "saveInput",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>保存输入流，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-输入流管理-新建-确定</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "name=inputName1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/input/saveInput?name=inputName1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/input/saveInput"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/InputController.java",
    "groupTitle": "输入流接口"
  },
  {
    "type": "POST",
    "url": "/api/input/updateInput",
    "title": "更新输入流",
    "group": "InputController",
    "name": "updateInput",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>更新输入流，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-输入流管理-编辑-确定</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=2&name=inputName2",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/input/updateInput?id=2&name=inputName2' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/input/updateInput"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/InputController.java",
    "groupTitle": "输入流接口"
  },
  {
    "type": "POST",
    "url": "/api/job/checkJob",
    "title": "校验唯一性",
    "group": "JobController",
    "name": "checkJob",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>校验唯一性，成功code返回200 </p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-计划任务管理-新建-校验合法性</font></li></p> <p><li><font color=\"red\">数据交换组件-计划任务管理-编辑-校验合法性</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID,新建时为空，编辑时必填，</p>"
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
        "content": "curl -i -X POST 'http://localhost:8045/api/job/checkJob' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码:{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Boolean",
            "optional": false,
            "field": "data",
            "description": "<p>校验结果{true:合法,false:不合法}</p>"
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
        "url": "/api/job/checkJob"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/JobController.java",
    "groupTitle": "计划任务接口"
  },
  {
    "type": "POST",
    "url": "/api/job/deleteJob",
    "title": "删除计划任务",
    "group": "JobController",
    "name": "deleteJob",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>删除计划任务，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-计划任务管理-删除</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/job/deleteJob?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/job/deleteJob"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/JobController.java",
    "groupTitle": "计划任务接口"
  },
  {
    "type": "POST",
    "url": "/api/job/disableJob",
    "title": "停用计划任务",
    "group": "JobController",
    "name": "disableJob",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>停用计划任务，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-计划任务管理-停用</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/job/disableJob?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/job/disableJob"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/JobController.java",
    "groupTitle": "计划任务接口"
  },
  {
    "type": "POST",
    "url": "/api/job/enableJob",
    "title": "启用计划任务",
    "group": "JobController",
    "name": "enableJob",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>启用计划任务，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-计划任务管理-启用</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/job/enableJob?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/job/enableJob"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/JobController.java",
    "groupTitle": "计划任务接口"
  },
  {
    "type": "POST",
    "url": "/api/job/getJob",
    "title": "获取计划任务详情",
    "group": "JobController",
    "name": "getJob",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>获取计划任务详情，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-计划任务管理-编辑</font></li></p> <p><li><font color=\"red\">数据交换组件-计划任务管理-详情</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/job/getJob?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象</p>"
          }
        ],
        "data对象字段数据:": [
          {
            "group": "data对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Id</p>"
          },
          {
            "group": "data对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":{\"id\":\"XXX\",\"name\":\"XXX\"}}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/job/getJob"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/JobController.java",
    "groupTitle": "计划任务接口"
  },
  {
    "type": "POST",
    "url": "/api/job/pageJob",
    "title": "分页搜索计划任务",
    "group": "JobController",
    "name": "pageJob",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>分页搜索计划任务，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-计划任务管理</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "field": "name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageNum",
            "description": "<p>查询页码，从1开始计算</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页展示的条数</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "pageNum=1&pageSize=10",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/job/pageJob?pageNum=1&pageSize=10' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象</p>"
          }
        ],
        "data对象字段数据:": [
          {
            "group": "data对象字段数据:",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>记录总数，前端可以根据此值和pageSize，pageNum计算其他分页参数</p>"
          },
          {
            "group": "data对象字段数据:",
            "type": "Object[]",
            "optional": false,
            "field": "pageList",
            "description": "<p>数组</p>"
          }
        ],
        "pageList数组每个对象字段数据:": [
          {
            "group": "pageList数组每个对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Id</p>"
          },
          {
            "group": "pageList数组每个对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":{\"total\":100,\"pageList\":[{\"id\":\"XXX\",\"name\":\"XXX\"}]}}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/job/pageJob"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/JobController.java",
    "groupTitle": "计划任务接口"
  },
  {
    "type": "POST",
    "url": "/api/job/runJob",
    "title": "运行计划任务",
    "group": "JobController",
    "name": "runJob",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>运行计划任务，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-计划任务管理-运行</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/job/runJob?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/job/runJob"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/JobController.java",
    "groupTitle": "计划任务接口"
  },
  {
    "type": "POST",
    "url": "/api/job/saveJob",
    "title": "保存计划任务",
    "group": "JobController",
    "name": "saveJob",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>保存计划任务，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-计划任务管理-新建-确定</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "name=jobName1&inputId=&inputName=inputName1&readerId=&readerName=mysqlreader&outputId=1&outputName=outputName1&writerId=&writerName=mysqlwriter",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/job/saveJob?name=jobName1&inputId=&inputName=inputName1&readerId=&readerName=mysqlreader&outputId=1&outputName=outputName1&writerId=&writerName=mysqlwriter' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/job/saveJob"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/JobController.java",
    "groupTitle": "计划任务接口"
  },
  {
    "type": "POST",
    "url": "/api/job/updateJob",
    "title": "更新计划任务",
    "group": "JobController",
    "name": "updateJob",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>更新计划任务，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-计划任务管理-编辑-确定</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=2&name=jobName2",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/job/updateJob?id=2&name=jobName2' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/job/updateJob"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/JobController.java",
    "groupTitle": "计划任务接口"
  },
  {
    "type": "POST",
    "url": "/api/output/checkOutput",
    "title": "校验唯一性",
    "group": "OutputController",
    "name": "checkOutput",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>校验唯一性，成功code返回200 </p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-输出流管理-新建-校验合法性</font></li></p> <p><li><font color=\"red\">数据交换组件-输出流管理-编辑-校验合法性</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID,新建时为空，编辑时必填，</p>"
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
        "content": "curl -i -X POST 'http://localhost:8045/api/output/checkOutput' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码:{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Boolean",
            "optional": false,
            "field": "data",
            "description": "<p>校验结果{true:合法,false:不合法}</p>"
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
        "url": "/api/output/checkOutput"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/OutputController.java",
    "groupTitle": "输出流接口"
  },
  {
    "type": "POST",
    "url": "/api/output/deleteOutput",
    "title": "删除输出流",
    "group": "OutputController",
    "name": "deleteOutput",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>删除输出流，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-输出流管理-删除</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/output/deleteOutput?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/output/deleteOutput"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/OutputController.java",
    "groupTitle": "输出流接口"
  },
  {
    "type": "POST",
    "url": "/api/output/getOutput",
    "title": "获取输出流详情",
    "group": "OutputController",
    "name": "getOutput",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>获取输出流详情，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-输出流管理-编辑</font></li></p> <p><li><font color=\"red\">数据交换组件-输出流管理-详情</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/output/getOutput?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象</p>"
          }
        ],
        "data对象字段数据:": [
          {
            "group": "data对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Id</p>"
          },
          {
            "group": "data对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":{\"id\":\"XXX\",\"name\":\"XXX\"}}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/output/getOutput"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/OutputController.java",
    "groupTitle": "输出流接口"
  },
  {
    "type": "POST",
    "url": "/api/output/pageOutput",
    "title": "分页搜索输出流",
    "group": "OutputController",
    "name": "pageOutput",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>分页搜索输出流，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-输出流管理</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "field": "name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageNum",
            "description": "<p>查询页码，从1开始计算</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页展示的条数</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "pageNum=1&pageSize=10",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/output/pageOutput?pageNum=1&pageSize=10' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象</p>"
          }
        ],
        "data对象字段数据:": [
          {
            "group": "data对象字段数据:",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>记录总数，前端可以根据此值和pageSize，pageNum计算其他分页参数</p>"
          },
          {
            "group": "data对象字段数据:",
            "type": "Object[]",
            "optional": false,
            "field": "pageList",
            "description": "<p>数组</p>"
          }
        ],
        "pageList数组每个对象字段数据:": [
          {
            "group": "pageList数组每个对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Id</p>"
          },
          {
            "group": "pageList数组每个对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":{\"total\":100,\"pageList\":[{\"id\":\"XXX\",\"name\":\"XXX\"}]}}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/output/pageOutput"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/OutputController.java",
    "groupTitle": "输出流接口"
  },
  {
    "type": "POST",
    "url": "/api/ouput/saveOutput",
    "title": "保存输出流",
    "group": "OutputController",
    "name": "saveOutput",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>保存输出流，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-输出流管理-新建-确定</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "name=outputName1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/output/saveOutput?name=outputName1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/output/saveOutput"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/OutputController.java",
    "groupTitle": "输出流接口"
  },
  {
    "type": "POST",
    "url": "/api/output/updateOutput",
    "title": "更新输出流",
    "group": "OutputController",
    "name": "updateOutput",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>更新输出流，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-输出流管理-编辑-确定</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=2&name=outputName2",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/output/updateOutput?id=2&name=outputName2' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/output/updateOutput"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/OutputController.java",
    "groupTitle": "输出流接口"
  },
  {
    "type": "POST",
    "url": "/api/plugin/checkPlugin",
    "title": "校验唯一性",
    "group": "PluginController",
    "name": "checkPlugin",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>校验唯一性，成功code返回200 </p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-插件管理-新建-校验合法性</font></li></p> <p><li><font color=\"red\">数据交换组件-插件管理-编辑-校验合法性</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID,新建时为空，编辑时必填，</p>"
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
        "content": "curl -i -X POST 'http://localhost:8045/api/plugin/checkPlugin' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码:{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Boolean",
            "optional": false,
            "field": "data",
            "description": "<p>校验结果{true:合法,false:不合法}</p>"
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
        "url": "/api/plugin/checkPlugin"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/PluginController.java",
    "groupTitle": "插件接口"
  },
  {
    "type": "POST",
    "url": "/api/plugin/deletePlugin",
    "title": "删除插件",
    "group": "PluginController",
    "name": "deletePlugin",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>删除插件，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-插件管理-删除</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/plugin/deletePlugin?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/plugin/deletePlugin"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/PluginController.java",
    "groupTitle": "插件接口"
  },
  {
    "type": "POST",
    "url": "/api/plugin/getPlugin",
    "title": "获取插件详情",
    "group": "PluginController",
    "name": "getPlugin",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>获取插件详情，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-插件管理-编辑</font></li></p> <p><li><font color=\"red\">数据交换组件-插件管理-详情</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/plugin/getPlugin?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象</p>"
          }
        ],
        "data对象字段数据:": [
          {
            "group": "data对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Id</p>"
          },
          {
            "group": "data对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":{\"id\":\"XXX\",\"name\":\"XXX\"}}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/plugin/getPlugin"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/PluginController.java",
    "groupTitle": "插件接口"
  },
  {
    "type": "POST",
    "url": "/api/plugin/pagePlugin",
    "title": "分页搜索插件",
    "group": "PluginController",
    "name": "pagePlugin",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>分页搜索插件，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-插件管理</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "field": "name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageNum",
            "description": "<p>查询页码，从1开始计算</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页展示的条数</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "pageNum=1&pageSize=10",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/plugin/pagePlugin?pageNum=1&pageSize=10' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象</p>"
          }
        ],
        "data对象字段数据:": [
          {
            "group": "data对象字段数据:",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>记录总数，前端可以根据此值和pageSize，pageNum计算其他分页参数</p>"
          },
          {
            "group": "data对象字段数据:",
            "type": "Object[]",
            "optional": false,
            "field": "pageList",
            "description": "<p>数组</p>"
          }
        ],
        "pageList数组每个对象字段数据:": [
          {
            "group": "pageList数组每个对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Id</p>"
          },
          {
            "group": "pageList数组每个对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":{\"total\":100,\"pageList\":[{\"id\":\"XXX\",\"name\":\"XXX\"}]}}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/plugin/pagePlugin"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/PluginController.java",
    "groupTitle": "插件接口"
  },
  {
    "type": "POST",
    "url": "/api/plugin/savePlugin",
    "title": "保存插件",
    "group": "PluginController",
    "name": "savePlugin",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>保存插件，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-插件管理-新建-确定</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "name=pluginName1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/plugin/savePlugin?name=pluginName1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/plugin/savePlugin"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/PluginController.java",
    "groupTitle": "插件接口"
  },
  {
    "type": "POST",
    "url": "/api/plugin/updatePlugin",
    "title": "更新插件",
    "group": "PluginController",
    "name": "updatePlugin",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>更新插件，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-插件 管理-编辑-确定</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=2&name=pluginName2",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/plugin/updatePlugin?id=2&name=pluginName2' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/plugin/updatePlugin"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/PluginController.java",
    "groupTitle": "插件接口"
  },
  {
    "type": "POST",
    "url": "/api/transformer/checkTransformer",
    "title": "校验唯一性",
    "group": "TransformerController",
    "name": "checkTransformer",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>校验唯一性，成功code返回200 </p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-交换清洗管理-新建-校验合法性</font></li></p> <p><li><font color=\"red\">数据交换组件-交换清洗管理-编辑-校验合法性</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID,新建时为空，编辑时必填，</p>"
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
        "content": "curl -i -X POST 'http://localhost:8045/api/transformer/checkTransformer' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码:{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Boolean",
            "optional": false,
            "field": "data",
            "description": "<p>校验结果{true:合法,false:不合法}</p>"
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
        "url": "/api/transformer/checkTransformer"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/TransformerController.java",
    "groupTitle": "交换清洗接口"
  },
  {
    "type": "POST",
    "url": "/api/transformer/deleteTransformer",
    "title": "删除交换清洗",
    "group": "TransformerController",
    "name": "deleteTransformer",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>删除交换清洗，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-交换清洗管理-删除</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/transformer/deleteTransformer?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/transformer/deleteTransformer"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/TransformerController.java",
    "groupTitle": "交换清洗接口"
  },
  {
    "type": "POST",
    "url": "/api/transformer/getTransformer",
    "title": "获取交换清洗详情",
    "group": "TransformerController",
    "name": "getTransformer",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>获取交换清洗详情，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-交换清洗管理-编辑</font></li></p> <p><li><font color=\"red\">数据交换组件-交换清洗管理-详情</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/transformer/getTransformer?id=1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象</p>"
          }
        ],
        "data对象字段数据:": [
          {
            "group": "data对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Id</p>"
          },
          {
            "group": "data对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":{\"id\":\"XXX\",\"name\":\"XXX\"}}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/transformer/getTransformer"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/TransformerController.java",
    "groupTitle": "交换清洗接口"
  },
  {
    "type": "POST",
    "url": "/api/transformer/pageTransformer",
    "title": "分页搜索交换清洗",
    "group": "TransformerController",
    "name": "pageTransformer",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>分页搜索交换清洗，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-交换清洗管理</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "field": "name",
            "description": "<p>名称</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageNum",
            "description": "<p>查询页码，从1开始计算</p>"
          },
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "pageSize",
            "description": "<p>每页展示的条数</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "pageNum=1&pageSize=10",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/transformer/pageTransformer?pageNum=1&pageSize=10' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回对象</p>"
          }
        ],
        "data对象字段数据:": [
          {
            "group": "data对象字段数据:",
            "type": "Integer",
            "optional": false,
            "field": "total",
            "description": "<p>记录总数，前端可以根据此值和pageSize，pageNum计算其他分页参数</p>"
          },
          {
            "group": "data对象字段数据:",
            "type": "Object[]",
            "optional": false,
            "field": "pageList",
            "description": "<p>数组</p>"
          }
        ],
        "pageList数组每个对象字段数据:": [
          {
            "group": "pageList数组每个对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Id</p>"
          },
          {
            "group": "pageList数组每个对象字段数据:",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":{\"total\":100,\"pageList\":[{\"id\":\"XXX\",\"name\":\"XXX\"}]}}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/transformer/pageTransformer"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/TransformerController.java",
    "groupTitle": "交换清洗接口"
  },
  {
    "type": "POST",
    "url": "/api/transformer/saveTransformer",
    "title": "保存交换清洗",
    "group": "TransformerController",
    "name": "saveTransformer",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>保存交换清洗，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-交换清洗管理-新建-确定</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "name=transformerName1",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/transformer/saveTransformer?name=transformerName1' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/transformer/saveTransformer"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/TransformerController.java",
    "groupTitle": "交换清洗接口"
  },
  {
    "type": "POST",
    "url": "/api/transformer/updateTransformer",
    "title": "更新交换清洗",
    "group": "TransformerController",
    "name": "updateTransformer",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>更新交换清洗，成功code返回200</p> <p><font color=\"red\">适用场景：</font></p>\t <p><li><font color=\"red\">数据交换组件-交换清洗管理-编辑-确定</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
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
            "description": "<p>ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>名称</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "id=2&name=transformerName2",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例: \t",
        "content": "curl -i -X POST 'http://localhost:8045/api/transformer/updateTransformer?id=2&name=transformerName2' -H \"Authorization:Bearer admin_token\"",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>提示信息</p>"
          },
          {
            "group": "返回结果:",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>null</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":200,\"msg\":\"请求成功\",\"data\":null}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX参数不规范\",\"data\":null}",
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
        "url": "/api/transformer/updateTransformer"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/TransformerController.java",
    "groupTitle": "交换清洗接口"
  },
  {
    "type": "POST",
    "url": "/login",
    "title": "登录用户（获取令牌）",
    "group": "UserController",
    "name": "login",
    "version": "1.0.0",
    "permission": [
      {
        "name": "user"
      }
    ],
    "description": "<p>登录用户（获取令牌），成功code返回200</p> <p><font color=\"red\">适用场景：</font></p> <p><li><font color=\"red\">XXX系统-XXX菜单-XXX页面-XXX按钮/链接</font></li></p>",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "ContentType",
            "defaultValue": "application/x-www-form-urlencoded",
            "description": "<p>请求类型</p>"
          },
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Accept",
            "defaultValue": "application/json;charset=UTF-8",
            "description": "<p>响应类型</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求头部示例: ",
          "content": "{ \n\t\t\"Content-Type\":\"application/x-www-form-urlencoded\", \n     \"Accept\":\"application/json;charset=UTF-8\"\n}",
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
            "field": "username",
            "description": "<p>用户名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>用户密码</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例:",
          "content": "username=admin&password=123456",
          "type": "String"
        }
      ]
    },
    "examples": [
      {
        "title": "命令行调用示例:  ",
        "content": "curl  -i -X  POST http://localhost:8045/login?username=admin&password=123456",
        "type": "curl"
      }
    ],
    "success": {
      "fields": {
        "返回结果:": [
          {
            "group": "返回结果:",
            "type": "Integer",
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
            "type": "String",
            "optional": false,
            "field": "token_type",
            "description": "<p>默认为bearer</p>"
          },
          {
            "group": "返回结果:",
            "type": "Long",
            "optional": false,
            "field": "expires_in",
            "description": "<p>过期时间</p>"
          },
          {
            "group": "返回结果:",
            "type": "String",
            "optional": false,
            "field": "access_token",
            "description": "<p>用户令牌 ,接下来所有访问该应用的请求都带上token，请在有效期内使用，如果即将过期，可以调用/refreshToken刷新token</p>"
          }
        ],
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回数据</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>说明</p>"
          },
          {
            "group": "Success 200",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功返回: ",
          "content": "{\"code\":0,\"msg\":\"请求成功\",\"access_token\":\"XXX\"}",
          "type": "json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败返回: ",
          "content": "{\"code\":400,\"msg\":\"XXX\",\"data\":null}",
          "type": "json"
        }
      ],
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "type": "Object",
            "optional": false,
            "field": "data",
            "description": "<p>返回数据</p>"
          },
          {
            "group": "Error 4xx",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>说明</p>"
          },
          {
            "group": "Error 4xx",
            "type": "Integer",
            "optional": false,
            "field": "code",
            "description": "<p>返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}</p>"
          }
        ]
      }
    },
    "sampleRequest": [
      {
        "url": "/login"
      }
    ],
    "filename": "./src/main/java/com/github/qq275860560/controller/UserController.java",
    "groupTitle": "用户接口"
  }
] });

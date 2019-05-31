var db, dbName = 'demoDb', dbVersion = 1, chatStore = 'chat';
// 创建indexedDB对象，兼容各种浏览器
var indexedDB = window.indexedDB || window.webkitIndexedDB || window.mozIndexedDB || window.msIndexedDB;
if (!indexedDB) {
    console.log("你的浏览器不支持IndexedDB");
}

// 配合游标遍历表中数据，并配合art-template生成html
function loadTableData() {
    var trans = db.transaction(chatStore, 'readwrite');
    var store = trans.objectStore(chatStore);
    var cursorRequest = store.openCursor();
    cursorRequest.onsuccess = function (e) {
        debugger
        var cursor = e.target.result;
        if (cursor) {
            console.log(cursor)
            cursor.continue(); // 游标继续往下 搜索，重复触发 onsuccess方法，如果到到返回null
        }
    }
}
// 添加聊天记录
function addChatMessage(chatMsg) {
    if (!db) {
        return;
    }
    // 创建一个事务
    var transaction = db.transaction(chatStore, 'readwrite');

    // 通过事务来获取store
    var store = transaction.objectStore(chatStore);

    var addRequest = store.add(chatMsg);
    addRequest.onsuccess = function (e) {
        console.log(e.target);
    }
    addRequest.onerror = function (e) {
        console.log(e.target.error);
    }
}

// 打开数据库
function openIndexedDB(callback) {
    // 打开一个数据库
    var request = indexedDB.open(dbName, dbVersion);

    // 打开失败
    request.onerror = function (e) {
        console.log(e.currentTarget.error.message);
    };

    // 打开成功！
    request.onsuccess = function (e) {
        db = e.target.result;
        console.log('成功打开DB');
        if (callback) {
            callback();
        }
    };

    // 打开成功后，如果版本有变化自动执行以下事件
    request.onupgradeneeded = function (e) {
        var db = e.target.result;
        if (!db.objectStoreNames.contains(chatStore)) {
            console.log("我需要创建一个新的存储对象");
            //如果表格不存在，创建一个新的表格（keyPath，主键 ； autoIncrement,是否自增），会返回一个对象（objectStore）
            var objectStore = db.createObjectStore(chatStore, {
                keyPath: "id",
                autoIncrement: true
            });

            //指定可以被索引的字段，unique字段是否唯一, 指定索引可以加快搜索效率。
            objectStore.createIndex("userId", "userId", {
                unique: false
            });
            objectStore.createIndex("groupId", "groupId", {
                unique: false
            });
        }
        console.log('数据库版本更改为： ' + dbVersion);
    };
}
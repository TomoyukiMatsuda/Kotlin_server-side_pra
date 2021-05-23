import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.mybatis.dynamic.sql.SqlBuilder
import org.mybatis.dynamic.sql.SqlBuilder.isEqualTo
import org.mybatis.dynamic.sql.SqlBuilder.where

// TODO: リスト5.4.2 の関数を定義するところから！
fun createSessionFactory(): SqlSessionFactory {
    val resource = "mybatis-config.xml"
    val inputStream = Resources.getResourceAsStream(resource)
    return SqlSessionFactoryBuilder().build(inputStream)
}

//fun main() {
//    // openSession()でセッションを開始
//    createSessionFactory().openSession().use { session ->
//        // Mapperオブジェクトを取得
//        val mapper = session.getMapper(UserMapper::class.java)
//        // クエリを実行する（id:100のユーザーを取得）
//        val user = mapper.selectByPrimaryKey(100)
//        println(user)
//        // select の場合は主キー検索では無く複数件のレコードを取得する可能性があるため、UserRecord の
//        // List型のオブジェクトとして結果が返ってきている
//        val userList = mapper.select {
//            // nameがJiroを検索
//            //where(name, SqlBuilder.isEqualTo("Jiro"))
//            // ageが25以上のカラムを条件として検索
//            where(age, SqlBuilder.isGreaterThanOrEqualTo(25))
//        }
//        println(userList)
//
//        val count = mapper.count {
//            // age が 25以上のユーザー数を取得
//            where(age, SqlBuilder.isGreaterThanOrEqualTo(25))
//            // 全件取得
//            // allRows()
//        }
//        println(count)
//    }
//
//    // ユーザーを作成する（insert）
////    val userList = listOf(UserRecord(106, "大塚", 27, "Hello"), UserRecord(107, "松本", 26, "こんちわ"))
////    createSessionFactory().openSession().use { session ->
////        val mapper = session.getMapper(UserMapper::class.java)
////        // insert(UserRecord型) ユーザー登録
////        //val count = mapper.insert(user)
////        // insertMultiple(Collection<UserRecord>) で複数件ユーザー登録
////        val count = mapper.insertMultiple(userList)
////        session.commit()
////        println("$count 行のレコード登録")
////    }
//
//    // Update 更新
//    val user = UserRecord(id = 105, profile = "小池です")
//    createSessionFactory().openSession().use { session ->
//        val mapper = session.getMapper(UserMapper::class.java)
//        // updateByPrimaryKeySelective は値が設定されているカラムのみ更新(null設定は更新されない)
//        val count = mapper.updateByPrimaryKeySelective(user)
//        // updateByPrimaryKey は全てのカラムを更新（未設定カラムはnullで更新）
//        session.commit()
//        println("$count 行のレコードを更新")
//    }
//
//    // こういう更新の仕方もある
//    createSessionFactory().openSession().use { session ->
//        val mapper = session.getMapper(UserMapper::class.java)
//        val count = mapper.update {
//            // profile を "プロフィールを更新"で更新
//            set(profile).equalTo("プロフィールを更新")
//            // 条件を指定、どのユーザーを更新するか
//            where(id, isEqualTo(104))
//        }
//        // 指定した主キーのユーザーを削除
//        val deleteCount = mapper.deleteByPrimaryKey(101)
//        session.commit()
//        println("$count 行のレコードを更新")
//        println("$count 行のレコードを削除")
//        val allUser = mapper.select {
//            allRows()
//        }
//        println(allUser)
//    }
//
//    // こういったupdateのやり方もある、
//    // Record オブジェクトで値が設定されているカラム（ここではprofile）のみ更新
////    val user = UserRecord(profile = "Good Morning")
////    createSessionFactory().openSession().use { session ->
////        val mapper = session.getMapper(UserMapper::class.java)
////        val count = mapper.update {
////            updateSelectiveColumns(user)
////            where(name, isEqualTo("Shiro"))
////        }
////        session.commit()
////        println("${count}行のレコードを更新しました")
////    }
//
//}

//class Person (private val name: String, private val age: Int) {
//    // thisはプライマリコンストラクタを指す、プライマリコンストラクタ が呼ばれる
//    constructor(name: String) : this(name, 20) // ①
//    // thisは ①のセカンダリコンストラクタを指す、①が呼ばれる
//    constructor() : this("マツモト") // ②
//
//    fun p() {
//        println("名前：$name 年齢：$age")
//    }
//}
//
//fun main() {
//    // ②のセカンダリコンストラクタを呼ぶ
//    val matsumoto = Person()
//    matsumoto.p()
//}

class Person (private val name: String, private val age: Int = 25) {
    fun p() {
        println("名前：$name 年齢：$age")
    }
}

fun main() {
    val tanaka = Person("田中")
    tanaka.p()
}
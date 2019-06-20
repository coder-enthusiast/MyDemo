package com.jqk.mydemo.jetpack.room

import androidx.room.*
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by jiqingke
 * on 2019/2/19
 */
@Dao
interface UserDao {
    // 插入
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: User): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: User): Long


    @Insert
    fun insertBothUsers(user1: User, user2: User)

    @Insert
    fun insertUsersAndFriends(arghuser: User, friends: List<User>)
    // 更新
    @Update
    fun updateUsers(vararg users: User)
    // 删除
    @Delete
    fun deleteUsers(vararg users: User)
    // 简单的查询
    @Query("SELECT * FROM users")
    fun loadAllUsers(): Single<Array<User>>
//    // 带参数的查询
//    @Query("SELECT * FROM users WHERE age > :minAge")
//    fun loadAllUsersOlderThan(minAge: Int): Array<User>
//
//    @Query("SELECT * FROM users WHERE age BETWEEN :minAge AND :maxAge")
//    fun loadAllUsersBetweenAges(minAge: Int, maxAge: Int): Array<User>
//
//    @Query("SELECT * FROM users WHERE first_name LIKE :search OR last_name LIKE :search")
//    fun findUserWithName(search: String): List<User>
//    // 返回列的子集
//    @Query("SELECT first_name, last_name FROM users")
//    fun loadFullName(): List<NameTuple>
//    // 传递一组参数
//    @Query("SELECT first_name, last_name FROM users WHERE region IN (:regions)")
//    fun loadUsersFromRegions(regions: List<String>): List<NameTuple>
//
//    @Query("SELECT first_name, last_name FROM users WHERE region IN (:regions)")
//    fun loadUsersFromRegionsSync(regions: List<String>): LiveData<List<User>>
//    // 可观察的查询
//    @Query("SELECT * from users where id = :id LIMIT 1")
//    fun loadUserById(id: Int): Flowable<User>
//    // 使用RxJava查询
//    // Emits the number of users added to the database.
//    @Insert
//    fun insertLargeNumberOfUsers(users: List<User>): Maybe<Int>
//
//    // Makes sure that the operation finishes successfully.
//    @Insert
//    fun insertLargeNumberOfUsers(vararg users: User): Completable
//
//    /* Emits the number of users removed from the database. Always emits at
//       least one user. */
//    @Delete
//    fun deleteAllUsers(users: List<User>): Single<Int>
//    // 直接光标访问
//    @Query("SELECT * FROM users WHERE age > :minAge LIMIT 5")
//    fun loadRawUsersOlderThan(minAge: Int): Cursor
//    // 查询多个表
//    @Query(
//            "SELECT * FROM book " +
//                    "INNER JOIN loan ON loan.book_id = book.id " +
//                    "INNER JOIN user ON user.id = loan.user_id " +
//                    "WHERE user.name LIKE :userName"
//    )
//    fun findBooksBorrowedByNameSync(userName: String): List<Book>
//
//    @Query(
//            "SELECT user.name AS userName, pet.name AS petName " +
//                    "FROM user, pet " +
//                    "WHERE user.id = pet.user_id"
//    )
//    fun loadUserAndPetNames(): LiveData<List<UserPet>>
//
//    // You can also define this class in a separate file.
//    data class UserPet(var userName: String?, var petName: String?)
}
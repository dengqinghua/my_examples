package golang

import (
	"errors"
	. "github.com/smartystreets/goconvey/convey"
	. "github.com/stretchr/testify/mock"
	"testing"
)

type DbMock struct {
	Mock
}

func (db *DbMock) Get(id int) (Account, error) {
	args := db.Called(id)

	return args.Get(0).(Account), args.Error(1)
}

func (db *DbMock) GetTwo(id int) (*Account, error) {
	args := db.Called(id)

	return args.Get(0).(*Account), args.Error(1)
}

// go test github.com/dengqinghua/golang/ -v -run TestMock
func TestMock(t *testing.T) {
	Convey("Test Mock", t, func() {
		Convey("when id is found", func() {
			Convey("it should return the account name", func() {
				dbMock := &DbMock{}
				// 在这里将Get方法做了一下 mock, 当她得到10的时候
				// 返回 Account的账户
				dbMock.On("Get", 10).Return(Account{"Ds", 10}, nil)
				So(GetUserNameByID(dbMock, 10), ShouldEqual, "Ds")
			})
		})

		Convey("when id is not found", func() {
			Convey("it should return empty string", func() {
				dbMock := &DbMock{}
				// 注意这里得写成 Account{}
				dbMock.On("Get", 100000).Return(
					Account{},
					errors.New("This is not existed"),
				)

				So(GetUserNameByID(dbMock, 100000), ShouldEqual, "")
			})
		})
	})
}

// go test github.com/dengqinghua/golang/ -v -run TestMockTwo
func TestMockTwo(t *testing.T) {
	Convey("Test Mock", t, func() {
		Convey("when id is not found", func() {
			Convey("it should return empty string", func() {
				dbMock := &DbMock{}
				// ~~注意这里可以写成nil~~
				// 并不能写成 nil... 还得写成 &Account{}, 不知道为什么
				// 理论上来说 nil 可以代表空指针啊...
				// 如果写成 nil 的话, 会抛出 interface {} is nil, not *golang.Account 这个错误
				dbMock.On("GetTwo", 100000).Return(&Account{}, errors.New("This is not existed"))

				So(GetUserNameByIDTwo(dbMock, 100000), ShouldEqual, "")
			})
		})
	})
}

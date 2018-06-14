package main

// 使用 struct 实现类似于 OO 的功能
// 部门
type Department struct {
	Name      string // 部门名字
	HeadCount int    // 部门人数
}

// 这里的 *Department 为 Receiver of DecrOneHeadCount
// 调用方式请见测试, Department 和 &Department 均可
func (department *Department) DecrOneHeadCount() {
	department.HeadCount -= 1
}

func newDepartment(name string, headCount int) Department {
	return Department{name, headCount}
}

func newDepartmentVersionTwo(name string, headCount int) Department {
	return Department{
		Name:      name,
		HeadCount: headCount,
	}
}

// 这里返回值为 *Department 代表什么? 我理解是对象本身
func newDepartmentVersionPointer(name string, headCount int) *Department {
	return &Department{
		Name:      name,
		HeadCount: headCount,
	}
}

// 内部有new 方法, 但是不推荐使用. 见

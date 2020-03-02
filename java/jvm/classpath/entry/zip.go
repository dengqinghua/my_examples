package entry

import (
	zipUtil "archive/zip"
	"errors"
	"io/ioutil"
)

// Zip is an implemet of Entry
type Zip struct {
	absPath string
}

// NewZip returns a zip entry instance
func NewZip(path string) *Zip {
	return &Zip{path}
}

func (zip *Zip) String() string {
	return zip.absPath
}

func (zip *Zip) readClass(className string) ([]byte, Entry, error) {
	r, err := zipUtil.OpenReader(zip.absPath)

	if err != nil {
		return nil, nil, err
	}

	defer r.Close()

	for _, f := range r.File {
		if f.Name == className {
			rc, err := f.Open()

			if err != nil {
				return nil, nil, err
			}

			data, err := ioutil.ReadAll(rc)

			if err != nil {
				return nil, nil, err
			} else {
				return data, zip, nil
			}
		}
	}

	return nil, nil, errors.New("Class Not Found " + className)
}

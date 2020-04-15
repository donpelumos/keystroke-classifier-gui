from sklearn.metrics import accuracy_score
import pandas as pd
from sklearn.neighbors import KNeighborsClassifier
from sklearn import preprocessing
from sklearn.model_selection import train_test_split
import sys


class KeyStrokeClassifierKNN:
    """Class used for carrying out classification of test key stroke data.
    It takes in 3 arguments.
    -   The first is the file path to the csv file containing the dataset.
    -   The second id the out of sample test feature to be classified which
        is a comma separated string of all the columns (features).
    -   The third is the knn model test ratio which specifies what percentage
        of the dataset should be used as test in generating the knn model.
        It is any decimal between 0.0 to 1.0.
    -   The fourth argument is the size of 'n' neighbours to be used"""

    def __init__(self, dataset_file_path, test_feature_string, knn_model_test_ratio, neighbour_size):
        self.dataset_file_path = dataset_file_path
        self.test_feature_string = test_feature_string
        self.knn_model_test_ratio = knn_model_test_ratio
        self.neighbour_size = neighbour_size

    def fetch_classification(self):
        keystroke_data = pd.read_csv(self.dataset_file_path)
        data = keystroke_data.iloc[:, 0:38]
        le = preprocessing.LabelEncoder()
        #encoded_value = le.fit_transform(keystroke_data.iloc[:, 38:39])
        target = keystroke_data['CLASS']
        sample_text_row = pd.DataFrame.transpose(pd.DataFrame(self.test_feature_string.split(",")))

        # sample_text_row = sample_text_row[0]
        data_train, data_test, target_train, target_test = train_test_split(data, target,
                                                                            test_size=float(self.knn_model_test_ratio),
                                                                            random_state=10)
        knn_model = KNeighborsClassifier(n_neighbors=int(self.neighbour_size))
        knn_model.fit(data_train, target_train)
        pred = knn_model.predict(data_test)
        prediction = knn_model.predict(sample_text_row)
        #print("KNeighbors accuracy score : ", accuracy_score(target_test, pred))
        print("FEATURE-CLASSIFICATION-RESULT ===>>> "+str(prediction[0]))
        return str(prediction[0])


def main(text1, text2, text3, text4):
    #printer = Printer(text1, text2)
    #printer.print_text()
    customClassifier = KeyStrokeClassifierKNN(text1, text2, text3, text4)
    return customClassifier.fetch_classification()


if __name__ == '__main__':
    # Map command line arguments to function arguments.
    main(*sys.argv[1:])

export const getCoursesForAdmin = `query { courses {id name description slug enabled createdAt profile {id name lastName user {id}}}}`;

export const getCoursesForStudent = `query { courses {id name description slug enabled createdAt}}`;

export const getCoursesForTeacher = `query { courses {id name description slug enabled createdAt profile {id name lastName user {id}}}}`;

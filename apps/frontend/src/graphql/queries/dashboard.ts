export const dashboard = `query { dashboard { courseCount latestProfiles { id name lastName user { email role enabled } } latestCourses { id name enabled user { email } } userCount } }`;

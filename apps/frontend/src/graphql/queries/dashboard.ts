export const dashboard = `query {
  dashboard {
    courseCount
    latestProfiles {
      id
      name
      lastName
      user {
        role
        createdAt
        enabled
      }
    }
    latestCourses {
      id
      name
      createdAt
      enabled
      profile {
        name
        lastName
      }
    }
    userCount
  }
}`;

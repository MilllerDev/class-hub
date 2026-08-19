export type PaginationProps = {
  currentPage: number;
  totalPages: number;
  last: boolean;
};

export type Items = {
  label: string;
  value: string;
};

export type ItemParams = { items: Items[] } & { param: string };

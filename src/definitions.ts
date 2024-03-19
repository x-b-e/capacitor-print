export interface PrintOptions {
  jobName: string;
}
export interface PrintPlugin {
    print(printOptions: PrintOptions): Promise<void>;
}
